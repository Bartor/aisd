let data = '' //paste data from program here
let r = '';
for (let a = 0; a < 5; a++) r += data.split('\n').filter((e, i) => i % 6 != 0).map(e => e.split(',')[2]).filter((e, i) => i % 5 == a).join(';').replace(/\./g, ',') + '\n';
console.log(r);