const crypto = require('crypto');
const algorithm = 'des-ecb';


const key = Buffer.from("d0e276d0144890d3", "hex");
const pw1 = 'bmlee654321';

var cipher = crypto.createCipheriv(algorithm, key, null);
let encrypted = cipher.update(pw1, 'utf8', 'hex')
encrypted += cipher.final('hex')

console.log(encrypted)
