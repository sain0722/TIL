const   fs = require('fs');
const   express = require('express');
const   ejs = require('ejs');
const   url = require('url');
const   mysql = require('mysql');
const   bodyParser = require('body-parser');
const   session = require('express-session');
const   multer = require('multer');
const  router = express.Router();

const   db = mysql.createConnection({
    host: 'localhost',        // DB서버 IP주소
    port: 3306,               // DB서버 Port주소
    user: 'bmlee',            // DB접속 아이디
    password: 'bmlee654321',  // DB암호
    database: 'bridge',         //사용할 DB명
    dateStrings: 'date'
});

//  -----------------------------------  상품리스트 기능 -----------------------------------------
// 등록된 상품리스트를 브라우져로 출력합니다.
const PrintCategoryProd = (req, res) => {
  let    htmlstream = '';
  let    htmlstream2 = '';
  let    sql_str, search_cat;
  const  query = url.parse(req.url, true).query;

       console.log('상품카테고리 조회 ' + query.category);

       if (req.session.auth)   {   // (로그인된 경우에만) 상품리스트를 출력합니다

           switch (query.category) {
               case 'fan' : search_cat = "선풍기"; break;
               case 'aircon': search_cat = "에어컨"; break;
               case 'aircool': search_cat = "냉풍기"; break;
               case 'fridge': search_cat = "냉장고"; break;
               case 'minisun': search_cat = "미니선풍기"; break;
               default: search_cat = "선풍기"; break;
           }

           htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');    // 헤더부분
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/navbar.ejs','utf8');  // 사용자메뉴
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/product.ejs','utf8'); // 카테고리별 제품리스트
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');  // Footer
           sql_str = "SELECT maker, pname, modelnum, rdate, price, pic, comment from u20_products where category='" + search_cat + "' order by rdate desc;"; // 상품조회SQL

           res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});

           db.query(sql_str, (error, results, fields) => {  // 상품조회 SQL실행
               if (error) { res.status(562).end("AdminPrintProd: DB query is failed"); }
               else if (results.length <= 0) {  // 조회된 상품이 없다면, 오류메시지 출력
                   htmlstream2 = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
                   res.status(562).end(ejs.render(htmlstream2, { 'title': '알리미',
                                      'warn_title':'상품조회 오류',
                                      'warn_message':'조회된 상품이 없습니다.',
                                      'return_url':'/' }));
                   }
              else {  // 조회된 상품이 있다면, 상품리스트를 출력
                     res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                                       'logurl': '/users/logout',
                                                       'loglabel': '로그아웃',
                                                       'regurl': '/users/profile',
                                                       'reglabel': req.session.who,
                                                       'category': search_cat,
                                                        prodata : results }));  // 조회된 상품정보
                 } // else
           }); // db.query()
       }
       else  {  // (로그인하지 않고) 본 페이지를 참조하면 오류를 출력
         htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
         res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                            'warn_title':'로그인 필요',
                            'warn_message':'상품검색을 하려면, 로그인이 필요합니다.',
                            'return_url':'/' }));
       }
};

// REST API의 URI와 핸들러를 매핑합니다.
router.get('/list', PrintCategoryProd);      // 상품리스트를 화면에 출력



//  -----------------------------------  상품 구매 기능 -----------------------------------------
// 구매버튼을 누른 항목을 구매하기 위한 화면을 브라우져로 출력합니다.
const PrintPurchaseProd = (req, res) => {

    let htmlstream = '';

    if (req.session.auth)   {   // (로그인된 경우에만) 상품리스트를 출력합니다
        
        htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');    // 헤더부분
        htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/navbar.ejs','utf8');  // 사용자메뉴
        htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/purchase_form.ejs','utf8'); // 카테고리별 제품리스트
        htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');  // Footer

        sql_str = "SELECT maker, modelnum, pname, price, pic, dcrate, amount, event from u20_products where modelnum ='"+ req.query.modelnum +"' "; // 상품조회SQL

        db.query(sql_str, (error, results, fields) => {  // 상품조회 SQL실행
                if (error) { res.status(562).end("AdminPrintProd: DB query is failed"); }

                else {  // 조회된 상품이 있다면, 상품리스트를 출력
                    res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                        'logurl': '/users/logout',
                        'loglabel': '로그아웃',
                        'regurl': '/users/profile',
                        'reglabel': req.session.who,
                        'point': req.session.point,
                        prodata : results }));  // 조회된 상품정보
                } // else
            }); // db.query()
        }
    else  {  // (로그인하지 않고) 본 페이지를 참조하면 오류를 출력
        htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
        res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
            'warn_title':'로그인 필요',
            'warn_message':'상품구매를 하려면, 로그인이 필요합니다.',
            'return_url':'/' }));
    }
}
const HandlePurchaseProd = (req, res) => {

    let htmlstream='';
    let body = req.body;

    let amount = body.amount;
    let point = body.point;
    let modelnum = body.modelnum;

    // sql문을 적을 변수
    let sql_prod_str = '';
    let sql_user_str = '';
    let sql_order_str = '';

    // orders에 insert할 변수 선언

    let order_id = new Date().toLocaleString();
    let buyer = req.session.uid;
    let address = body.address;
    let price = body.price;
    let howmany = body.howmany;

    console.log("구매확정 완료!!")
    // 재고 1개 차감
    amount = amount - 1;

    if (amount < 0) {
        console.log("재고가 소진되었습니다.");
        htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
        res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
            'warn_title':'재고 소진',
            'warn_message':'재고가 소진되었습니다. 관리자에게 문의하세요.',
            'return_url':'/' }));
    }
    else if (point == 0) {
        console.log("포인트가 부족합니다.");
        htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
        res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
            'warn_title':'포인트 부족',
            'warn_message':'포인트가 부족합니다. 포인트를 충전하세요.',
            'return_url':'/' }));
    }
    else {

        sql_prod_str = "UPDATE u20_products SET amount = '"+ amount +"' where modelnum = '"+ modelnum + "'";
        sql_user_str = "UPDATE u20_users SET point = '"+ point +"' where uid = '"+ req.session.uid +"' ";
        console.log("SQL1: " + sql_prod_str);
        console.log("SQL2: " + sql_user_str);
        db.query(sql_prod_str, (error, results, fields) => {
            if (error) {
                res.status(562).end("Wrong SQL_prod_str");
            } else {
                console.log("재고 차감 완료!!");
            }
        });

        /**
         * Order 테이블에 INSERT할 쿼리문
         * 오류로 인하여 잠시 보류.
         */

        // console.log(order_id)
        // console.log(buyer)
        // console.log(modelnum)
        // console.log(price)
        // console.log(address)
        // console.log(howmany)
        //
        // db.query('INSERT INTO u20_orders (order_id, buyer, itemid, price, addr, howmany) VALUES (?, ?, ?, ?, ?, ?)', [order_id, buyer, modelnum, price, address, howmany], (error, results, fields) => {
        //     if (error) {
        //         res.status(562).end("Wrong SQL_order_str");
        //     } else {
        //         console.log("order table 등록 완료!!");
        //     }
        // });

        db.query(sql_user_str, (error, results, fields) => {
            if (error) {
                res.status(562).end("Wrong SQL_user_str");
            } else {
                console.log("포인트 차감 완료!!");
                res.redirect('/');
            }
        });


        // 세션의 point값 변경
        req.session.point = point;

    }

}

router.get('/purchase', PrintPurchaseProd);
router.post('/purchase', HandlePurchaseProd);

module.exports = router;
