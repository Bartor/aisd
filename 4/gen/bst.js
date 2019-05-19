function numToString(n, limit) {
    const alphabet = "abcdefghijklmnoupqrstuvwxyz";
    let pos = 0;
    while (n > limit) {
        n -= limit;
        pos++;
    }
    if (alphabet[pos] === undefined) throw "This limit is too low";
    return Array(n).fill(alphabet[pos]).join("");
}

function generate(n) {
    let res = [];
    let arrays = [n];
    let newArrays = [];
    while (res.length != n.length) {
        for (let a of arrays) {
            let mid = Math.floor(a.length/2);
            if (a.length == 0) continue;
            res.push(a[mid]);
            newArrays.push(a.slice(mid+1));
            newArrays.push(a.slice(0, mid));
        }
        [arrays, newArrays] = [newArrays, []];
    }
    return res;
}

//generate 10000 lines of example data
console.log(generate(Array(8096).fill(0).map((e, i) => i)).map(e => numToString(e, 300)).join("\n"));