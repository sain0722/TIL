const   fs = require('fs');
const   express = require('express');
const   ejs = require('ejs');
const   mysql = require('mysql');
const   bodyParser = require('body-parser');
const   session = require('express-session');
const   multer = require('multer');
const upload = multer({dest: __dirname + '/../public/images/uploads/products'});  // 업로드 디렉터리를 설정한다.
const   router = express.Router();

const   db = mysql.createConnection({
    host: 'localhost',        // DB서버 IP주소
    port: 3306,               // DB서버 Port주소
    user: 'gbdbuser',            // DB접속 아이디
    password: 'jobbr1dge',  // DB암호
    database: 'bridge'         //사용할 DB명
});

//  -----------------------------------  상품리스트 기능 -----------------------------------------
// (관리자용) 등록된 상품리스트를 브라우져로 출력합니다.
const AdminPrintProd = (req, res) => {
  let    htmlstream = '';
  let    htmlstream2 = '';
  let    sql_str;

       if (req.session.auth && req.session.admin)   {   // 관리자로 로그인된 경우에만 처리한다
           htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');    // 헤더부분
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminbar.ejs','utf8');  // 관리자메뉴
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminproduct.ejs','utf8'); // 괸리자메인화면
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');  // Footer
           sql_str = "SELECT itemid, category, maker, pname, modelnum, rdate, price, amount from u20_products order by rdate desc;"; // 상품조회SQL

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
                                                        prodata : results }));  // 조회된 상품정보
                 } // else
           }); // db.query()
       }
       else  {  // (관리자로 로그인하지 않고) 본 페이지를 참조하면 오류를 출력
         htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
         res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                            'warn_title':'상품등록기능 오류',
                            'warn_message':'관리자로 로그인되어 있지 않아서, 상품등록 기능을 사용할 수 없습니다.',
                            'return_url':'/' }));
       }

};

//  -----------------------------------  상품등록기능 -----------------------------------------
// 상품등록 입력양식을 브라우져로 출력합니다.
const PrintAddProductForm = (req, res) => {
  let    htmlstream = '';

       if (req.session.auth && req.session.admin) { // 관리자로 로그인된 경우에만 처리한다
         htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');    // 헤더부분
         htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminbar.ejs','utf8');  // 관리자메뉴
         htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/product_form.ejs','utf8'); // 괸리자메인화면
         htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');  // Footer

         res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});
         res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                           'logurl': '/users/logout',
                                           'loglabel': '로그아웃',
                                           'regurl': '/users/profile',
                                           'reglabel': req.session.who }));
       }
       else {
         htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
         res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                            'warn_title':'상품등록기능 오류',
                            'warn_message':'관리자로 로그인되어 있지 않아서, 상품등록 기능을 사용할 수 없습니다.',
                            'return_url':'/' }));
       }

};

// 상품등록 양식에서 입력된 상품정보를 신규로 등록(DB에 저장)합니다.
const HanldleAddProduct = (req, res) => {  // 상품등록
  let    body = req.body;
  let    htmlstream = '';
  let    datestr, y, m, d, regdate;
  let    prodimage = '/images/uploads/products/'; // 상품이미지 저장디렉터리
  let    picfile = req.file;
  let    result = { originalName  : picfile.originalname,
                   size : picfile.size     }

       console.log(body);     // 이병문 - 개발과정 확인용(추후삭제).

       if (req.session.auth && req.session.admin) {
           if (body.itemid == '' || datestr == '') {
             console.log("상품번호가 입력되지 않아 DB에 저장할 수 없습니다.");
             res.status(561).end('<meta charset="utf-8">상품번호가 입력되지 않아 등록할 수 없습니다');
          }
          else {

              prodimage = prodimage + picfile.filename;
              regdate = new Date();
              db.query('INSERT INTO u20_products (itemid, category, maker, pname, modelnum,rdate,price,dcrate,amount,event,pic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)',
                    [body.itemid, body.category, body.maker, body.pname, body.modelnum, regdate,
                     body.price, body.dcrate, body.amount, body.event, prodimage], (error, results, fields) => {
               if (error) {
                   htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
                   res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                                 'warn_title':'상품등록 오류',
                                 'warn_message':'상품으로 등록할때 DB저장 오류가 발생하였습니다. 원인을 파악하여 재시도 바랍니다',
                                 'return_url':'/' }));
                } else {
                   console.log("상품등록에 성공하였으며, DB에 신규상품으로 등록하였습니다.!");
                   res.redirect('/adminprod/list');
                }
           });
       }
      }
     else {
         htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
         res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                            'warn_title':'상품등록기능 오류',
                            'warn_message':'관리자로 로그인되어 있지 않아서, 상품등록 기능을 사용할 수 없습니다.',
                            'return_url':'/' }));
       }
};

const PrintAdminUserList = (req, res) => {
  let    htmlstream = '';
  let    htmlstream2 = '';
  let    sql_str;

       if (req.session.auth && req.session.admin)   {   // 관리자로 로그인된 경우에만 처리한다
           htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');    // 헤더부분
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminbar.ejs','utf8');  // 관리자메뉴
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminuserlist.ejs','utf8'); // 괸리자메인화면
           htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');  // Footer
           sql_str = "select username, userid, pwd, phone, address, usertype, birth from u20_users;"; // 유저조회SQL



           res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});

           db.query(sql_str, (error, results, fields) => {  // 상품조회 SQL실행
               if (error) { res.status(562).end("AdminPrintProd: DB query is failed"); }
               else if (results.length <= 0) {  // 조회된 상품이 없다면, 오류메시지 출력
                   htmlstream2 = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
                   res.status(562).end(ejs.render(htmlstream2, { 'title': '알리미',
                                      'warn_title':'회원조회 오류',
                                      'warn_message':'조회된 회원이 없습니다.',
                                      'return_url':'/' }));
                   }
              else {  // 조회된 상품이 있다면, 상품리스트를 출력
                     res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                                       'logurl': '/users/logout',
                                                       'loglabel': '로그아웃',
                                                       'regurl': '/users/profile',
                                                       'reglabel': req.session.who,
                                                        prodata : results }));  // 조회된 상품정보
                 } // else
           }); // db.query()
       }
       else  {  // (관리자로 로그인하지 않고) 본 페이지를 참조하면 오류를 출력
         htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
         res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                            'warn_title':'상품등록기능 오류',
                            'warn_message':'관리자로 로그인되어 있지 않아서, 상품등록 기능을 사용할 수 없습니다.',
                            'return_url':'/' }));
       }

};

router.get('/users',PrintAdminUserList);

const HandleAdminUserList = (req,res)=>{
  let htmlstream = '';
  let htmlstream2 = '';
  let body = req.body;
  let userid, userpass, username;
  let useraddress, userphone, usertype;
  let userbirth;
  let userpoint;


  if (req.session.auth &&req.session.admin) {  // true :로그인된 상태,  false : 로그인안된 상태
    htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');
    htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminbar.ejs','utf8');  // 관리자메뉴
    htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/prod_profile.ejs','utf8');
    htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');
    sql_str = "SELECT * from u20_users where username = '"+body.uname+"';"; // 상품조회SQL
    res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});

    db.query(sql_str, (error, results, fields) => {  // 상품조회 SQL실행
        if (error) { res.status(562).end("AdminPrintProd: DB query is failed"); }
        else if (results.length <= 0) {  // 조회된 상품이 없다면, 오류메시지 출력
            htmlstream2 = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
            res.status(562).end(ejs.render(htmlstream2, { 'title': '알리미',
                               'warn_title':'유저조회 오류',
                               'warn_message':'조회된 유저가 없습니다.',
                               'return_url':'/adminprod/users' }));
            }
       else {  // 조회된 상품이 있다면, 상품리스트를 출력
         console.log(results);
         results.forEach((item, index) => {
           userid = item.userid;  userpass = item.pwd; username = item.username;
           useraddress = item.address; userphone = item.phone; usertype = item.usertype;
           userbirth = item.birth; userpoint = item.point;
          });
              res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                                'logurl': '/users/logout',
                                                'loglabel': '로그아웃',
                                                'regurl': '/users/profile',
                                                'reglabel': req.session.who,
                                                 prodata : results
                                                 }));  // 조회된 상품정보

          } // else
    }); // db.query()
}

};
router.post('/profilelist',HandleAdminUserList)

// REST API의 URI와 핸들러를 매핑합니다.
const HandleAdminProfile = (req,res) =>{
  let htmlstream = '';
  let htmlstream2 = '';
  let body = req.body;
  let session = req.session;
  let userid, userpass, username;
  let useraddress, userphone, usertype;
  let userbirth;
  let userpoint;
  console.log(body.userid);
  db.query('UPDATE u20_users SET pwd=?, address=?, phone=?, birth=?, usertype=?, point=? WHERE userid=?',
          [body.pw1, body.address, body.phone, body.birth, body.usertype, body.point, body.userid], (err,results,fields) =>{
          if(err){
                    htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
                    res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                                                            'warn_title':'개인정보 수정 오류',
                                                            'warn_message':'비밀번호가 틀렸습니다. 다시 확인하시기 바랍니다.',
                                                            'return_url':'/' }));
                                       }
          else{
            session.address = body.address;
            session.phone = body.phone;
            session.birth = body.birth;
            session.usertype = body.usertype;
            session.point = body.point;
            res.redirect('/adminprod/users');
          }

            });
};

const HandleUsersDelete = (req,res) =>{
  let body = req.body;
  let session = req.session;
  let userid, userpass, username;
  let useraddress, userphone, usertype;
  let userbirth;
  console.log(body.username);
  db.query('DELETE FROM u20_users WHERE username=?',
          [body.username], (err,results,fields) =>{
          if (err) { res.status(562).end("AdminPrintProd: DB query is failed"); }
          else if (results.length <= 0) {  // 조회된 상품이 없다면, 오류메시지 출력
              htmlstream2 = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
              res.status(562).end(ejs.render(htmlstream2, { 'title': '알리미',
                                 'warn_title':'회원조회 오류',
                                 'warn_message':'조회된 회원이 없습니다.',
                                 'return_url':'/' }));
              }
          else{
            res.redirect('/adminprod/users');
          }

            });
};

const HandleProductDelete = (req,res) =>{
  let body = req.body;
  let session = req.session
  console.log(body.itemid);
  db.query('DELETE FROM u20_products WHERE itemid=?',
          [body.itemid], (err,results,fields) =>{
          if (err) { res.status(562).end("AdminPrintProd: DB query is failed"); }
          else if (results.length <= 0) {  // 조회된 상품이 없다면, 오류메시지 출력
              htmlstream2 = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
              res.status(562).end(ejs.render(htmlstream2, { 'title': '알리미',
                                 'warn_title':'회원조회 오류',
                                 'warn_message':'조회된 회원이 없습니다.',
                                 'return_url':'/' }));
              }
          else{
            res.redirect('/adminprod/list');
          }

            });
};

const PrintModifyForm = (req,res)=>{
  let htmlstream = '';
  let htmlstream2 = '';
  let body = req.body;
  let category, maker, pname, modelnum, rdate, price, dcrate, event, pic, itemid;


  if (req.session.auth &&req.session.admin) {  // true :로그인된 상태,  false : 로그인안된 상태
    console.log(body.itemid);
    htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');
    htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/adminbar.ejs','utf8');  // 관리자메뉴
    htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/product_form_modify.ejs','utf8');
    htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');
    sql_str = "SELECT * from u20_products where itemid = '"+body.itemid+"';"; // 상품조회SQL
    res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});

    db.query(sql_str, (error, results, fields) => {  // 상품조회 SQL실행
        if (error) { res.status(562).end("AdminPrintProd: DB query is failed"); }
        else if (results.length <= 0) {  // 조회된 상품이 없다면, 오류메시지 출력
            htmlstream2 = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
            res.status(562).end(ejs.render(htmlstream2, { 'title': '알리미',
                               'warn_title':'상품조회 오류',
                               'warn_message':'조회된 상품이 없습니다.',
                               'return_url':'/adminprod/list' }));
            }
       else {  // 조회된 상품이 있다면, 상품리스트를 출력
         console.log(results);
         results.forEach((item, index) => {
           category = item.category;  maker = item.maker; pname = item.pname;
           modelnum = item.modelnum; price = item.price; dcrate = item.dcrate;
           event = item.event; itemid = item.itemid;
          });
              res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                                'logurl': '/users/logout',
                                                'loglabel': '로그아웃',
                                                'regurl': '/users/profile',
                                                'reglabel': req.session.who,
                                                 prodata : results
                                                 }));  // 조회된 상품정보

          } // else
    }); // db.query()
}

};

router.post('/modifyform',PrintModifyForm);

const HandleModifyForm = (req,res) =>{
  let htmlstream = '';
  let htmlstream2 = '';
  let body = req.body;
  let session = req.session;
  let itemid;
  let category, maker, pname, modelnum, rdate, price, dcrate, event;
  let amount;

  if (req.session.auth && req.session.admin) {
    if (body.itemid == '') {
      console.log("상품번호가 입력되지 않아 DB에 저장할 수 없습니다.");
      res.status(561).end('<meta charset="utf-8">상품번호가 입력되지 않아 등록할 수 없습니다');
   }
   else {
     console.log(body.itemid);


  db.query('UPDATE u20_products SET category=?, maker=?, pname=?, modelnum=?, price=?, dcrate=?, amount=?, event=? WHERE itemid=?',
          [body.category, body.maker, body.pname, body.modelnum, body.price, body.dcrate, body.amount, body.event, body.itemid], (err,results,fields) =>{
          if(err){
                    htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
                    res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                                                            'warn_title':'상품 수정 오류',
                                                            'warn_message':'상품번호가 틀렸습니다. 다시 확인하시기 바랍니다.',
                                                            'return_url':'/' }));
                                       }
          else{
            session.itemid = body.itemid;
            session.category = body.category;
            session.maker = body.maker;
            session.pname = body.pname;
            session.modelnum = body.modelnum;
            session.price = body.price;
            session.amount = body.amount;
            session.dcrate = body.dcrate;
            session.event = body.event;
            res.redirect('/adminprod/list');
          }

            });
          }
        }
        else {
            htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
            res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                               'warn_title':'상품등록기능 오류',
                               'warn_message':'관리자로 로그인되어 있지 않아서, 상품등록 기능을 사용할 수 없습니다.',
                               'return_url':'/' }));
          }

};

router.post('/productlist',HandleModifyForm);

router.post('/list',HandleProductDelete);
router.post('/users',HandleUsersDelete);
router.post('/profile',HandleAdminProfile);  // 정보변경내용을 DB에 저장
router.get('/form', PrintAddProductForm);   // 상품등록화면을 출력처리
router.post('/product', upload.single('photo'), HanldleAddProduct);    // 상품등록내용을 DB에 저장처리
router.get('/list', AdminPrintProd);      // 상품리스트를 화면에 출력
// router.get('/', function(req, res) { res.send('respond with a resource 111'); });

module.exports = router;
