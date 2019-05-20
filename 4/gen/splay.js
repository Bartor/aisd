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

function generate(n, reps) {
    let res = [];
    for (let a of n) {
        let i = reps;
        while (i-->0) res.push(a);
    }
    return res;
}

console.log(generate(Array(1000).fill(null).map((e, i) => i), 10).map(e => numToString(300)));