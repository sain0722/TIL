const   fs = require('fs');
const   express = require('express');
const   ejs = require('ejs');
const   mysql = require('mysql');
const   bodyParser = require('body-parser');
const   session = require('express-session');

router.use(bodyParser.urlencoded({ extended: false }));

const   db = mysql.createConnection({
    host: 'localhost',        // DB서버 IP주소
    port: 3306,               // DB서버 Port주소
    user: 'bmlee',            // DB접속 아이디
    password: 'bmlee654321',  // DB암호
    database: 'bridge'         //사용할 DB명
});

//  -----------------------------------  회원가입기능 -----------------------------------------
// 회원가입 입력양식을 브라우져로 출력합니다.
const PrintRegistrationForm = (req, res) => {
  let    htmlstream = '';

       htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/navbar.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/reg_form.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');
       res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});

       if (req.session.auth) {  // true :로그인된 상태,  false : 로그인안된 상태
           res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                             'logurl': '/users/logout',
                                             'loglabel': '로그아웃',
                                             'regurl': '/users/profile',
                                             'reglabel':req.session.who }));
       }
       else {
          res.end(ejs.render(htmlstream, { 'title' : '쇼핑몰site',
                                          'logurl': '/users/auth',
                                          'loglabel': '로그인',
                                          'regurl': '/users/reg',
                                          'reglabel':'회원가입' }));
       }

};

// 회원가입 양식에서 입력된 회원정보를 신규등록(DB에 저장)합니다.
const HandleRegistration = (req, res) => {  // 회원가입
let body = req.body;
let htmlstream='';

    console.log(body.uid);     // 임시로 확인하기 위해 콘솔에 출력해봅니다.
    console.log(body.pw1);
    console.log(body.uname);
    console.log(body.phone);
    console.log(body.birth);


    if (body.uid == '' || body.pw1 == '') {
         console.log("데이터입력이 되지 않아 DB에 저장할 수 없습니다.");
         res.status(561).end('<meta charset="utf-8">데이터가 입력되지 않아 가입을 할 수 없습니다');
    }
    else {
       db.query('INSERT INTO u20_users (userid, pwd, username, address, phone, birth, usertype) VALUES (?, ?, ?, ?, ?, ?, ?)', [body.uid, body.pw1, body.uname, body.address, body.phone, body.birth, body.usertype], (error, results, fields) => {
          if (error) {
            // console.log(error);
            console.log(req.session.address);
            htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
            res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                               'warn_title':'회원가입 오류',
                               'warn_message':'이미 회원으로 등록되어 있습니다. 바로 로그인을 하시기 바랍니다.',
                               'return_url':'/' }));
          } else {
           console.log("회원가입에 성공하였으며, DB에 신규회원으로 등록하였습니다.!");
           res.redirect('/');
          }
       });

    }
};

// REST API의 URI와 핸들러를 매핑합니다.
router.get('/reg', PrintRegistrationForm);   // 회원가입화면을 출력처리
router.post('/reg', HandleRegistration);   // 회원가입내용을 DB에 등록처리
router.get('/', function(req, res) { res.send('respond with a resource 111'); });

// ------------------------------------  로그인기능 --------------------------------------

// 로그인 화면을 웹브라우져로 출력합니다.
const PrintLoginForm = (req, res) => {
  let    htmlstream = '';

       htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/navbar.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/login_form.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');
       res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});
       console.log(req.session.address);

       if (req.session.auth) {  // true :로그인된 상태,  false : 로그인안된 상태
           res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                             'logurl': '/users/logout',
                                             'loglabel': '로그아웃',
                                             'regurl': '/users/profile',
                                             'reglabel': req.session.who }));
       }
       else {
          res.end(ejs.render(htmlstream, { 'title' : '쇼핑몰site',
                                          'logurl': '/users/auth',
                                          'loglabel': '로그인',
                                          'regurl': '/users/reg',
                                          'reglabel':'회원가입' }));
       }

};

// 로그인을 수행합니다. (사용자인증처리)
const HandleLogin = (req, res) => {
  let body = req.body;
  let userid, userpass, username;
  let useraddress, userphone, usertype;
  let userbirth;
  let userpoint;
  let sql_str;
  let htmlstream = '';

      console.log(body.uid);
      console.log(body.pass);
      if (body.uid == '' || body.pass == '') {
         console.log("아이디나 암호가 입력되지 않아서 로그인할 수 없습니다.");
         res.status(562).end('<meta charset="utf-8">아이디나 암호가 입력되지 않아서 로그인할 수 없습니다.');
      }
      else {
       sql_str = "SELECT * from u20_users where userid ='"+ body.uid +"' and pwd='" + body.pass + "';";
       console.log("SQL: " + sql_str);
       db.query(sql_str, (error, results, fields) => {
         if (error) { res.status(562).end("Login Fail as No id in DB!"); }
         else {
            if (results.length <= 0) {  // select 조회결과가 없는 경우 (즉, 등록계정이 없는 경우)
                  htmlstream = fs.readFileSync(__dirname + '/../views/alert.ejs','utf8');
                  res.status(562).end(ejs.render(htmlstream, { 'title': '알리미',
                                     'warn_title':'로그인 오류',
                                     'warn_message':'등록된 계정이나 암호가 틀립니다.',
                                     'return_url':'/' }));
             } else {  // select 조회결과가 있는 경우 (즉, 등록사용자인 경우)
               results.forEach((item, index) => {
                  userid = item.userid;  userpass = item.pwd; username = item.username;
                  useraddress = item.address; userphone = item.phone; usertype = item.usertype;
                  userbirth = item.birth; userpoint = item.point;

                  console.log("DB에서 로그인성공한 ID/암호:%s/%s", userid, userpass);
                  if (body.uid == userid && body.pass == userpass) {
                     req.session.auth = 99;      // 임의로 수(99)로 로그인성공했다는 것을 설정함
                     req.session.who = username;
                     req.session.uid = userid;
                     req.session.address = useraddress;
                     req.session.phone = userphone;
                     req.session.usertype = usertype;
                     req.session.birth = userbirth;
                     req.session.point = userpoint;
                       // 인증된 사용자명 확보 (로그인후 이름출력용)
                     if (body.uid == 'admin')    // 만약, 인증된 사용자가 관리자(admin)라면 이를 표시
                          req.session.admin = true;
                     res.redirect('/');
                  }
                }); /* foreach */
              } // else
            }  // else
       });
   }
}


// REST API의 URI와 핸들러를 매핑합니다.
//  URI: http://xxxx/users/auth
router.get('/auth', PrintLoginForm);   // 로그인 입력화면을 출력
router.post('/auth', HandleLogin);     // 로그인 정보로 인증처리

// ------------------------------  로그아웃기능 --------------------------------------

const HandleLogout = (req, res) => {
       req.session.destroy();     // 세션을 제거하여 인증오작동 문제를 해결
       res.redirect('/');         // 로그아웃후 메인화면으로 재접속
}

// REST API의 URI와 핸들러를 매핑합니다.
router.get('/logout', HandleLogout);       // 로그아웃 기능


// --------------- 정보변경 기능을 개발합니다 --------------------
const PrintProfile = (req, res) => {
  let    htmlstream = '';
  let body = req.body;
  let session = req.session;
  console.log(session.uid);     // 임시로 확인하기 위해 콘솔에 출력해봅니다.
  // console.log(session.pw1);
  // console.log(session.uname);
  // console.log(session.phone);
  // console.log(session.birth);

  // let uid = req.session.uid;
       htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/navbar.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/profile.ejs','utf8');
       htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');
       res.writeHead(200, {'Content-Type':'text/html; charset=utf8'});


       if (req.session.auth) {  // true :로그인된 상태,  false : 로그인안된 상태
           res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                             'logurl': '/users/logout',
                                             'loglabel': '로그아웃',
                                             'regurl': '/users/profile',
                                             'reglabel': req.session.who,
                                             'id':req.session.uid,
                                             'address' : req.session.address,
                                             'phone' : req.session.phone,
                                             'usertype' : req.session.usertype,
                                             'birth' : req.session.birth,
                                             'point' : req.session.point}));

       }
       else {
          res.end(ejs.render(htmlstream, { 'title' : '쇼핑몰site',
                                          'logurl': '/users/auth',
                                          'loglabel': '로그인',
                                          'regurl': '/users/reg',
                                          'reglabel':'회원가입' }));
       }

}

router.get('/profile', PrintProfile);     // 정보변경화면을 출력

const HandleProfile = (req,res) =>{
  let body = req.body;
  let session = req.session;
  let userid, userpass, username;
  let useraddress, userphone, usertype;
  let userbirth;
  let userpoint;
  db.query('UPDATE u20_users SET pwd=?, address=?, phone=?, birth=?, usertype=?, point=? WHERE userid=?',
          [body.pw1, body.address, body.phone, body.birth, body.usertype, body.point, session.uid], (err,results,fields) =>{
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
            res.redirect('/');
          }

            });
};
router.post('/profile',HandleProfile);  // 정보변경내용을 DB에 저장

const Handlesearch = (req,res) =>{
  let    htmlstream = '';
  let htmlstream2 = '';
  let body = req.body;
  let session = req.session;
  let itemid, category, maker, pname, modelnum;
  let rdate, price, dcrate, amount, pic;
  htmlstream = fs.readFileSync(__dirname + '/../views/header.ejs','utf8');
  htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/navbar.ejs','utf8');
  htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/search.ejs','utf8');
  htmlstream = htmlstream + fs.readFileSync(__dirname + '/../views/footer.ejs','utf8');

  if (req.session.auth) {  // true :로그인된 상태,  false : 로그인안된 상태
    sql_str = "SELECT * from u20_products where pname = '"+body.pname+"';"; // 상품조회SQL
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
         console.log(results);
         results.forEach((item, index) => {
            itemid = item.itemid;  category = item.category; maker = item.maker;
            pname = item.pname; modelnum = item.modelnum; rdate = item.rdate;
            price = item.price; dcrate = item.dcreate; amount = item.amount;
          });
              res.end(ejs.render(htmlstream,  { 'title' : '쇼핑몰site',
                                                'logurl': '/users/logout',
                                                'loglabel': '로그아웃',
                                                'regurl': '/users/profile',
                                                'reglabel': req.session.who,
                                                 prodata : results,
                                                 'pname':pname }));  // 조회된 상품정보

          } // else
    }); // db.query()
}
};


router.post('/search',Handlesearch);

module.exports = router;
