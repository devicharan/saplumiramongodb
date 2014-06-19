function reduce(key, counts) {
    var cnt = 0;
    // loop through call count values
    for (var i = 0; i < counts.length; i++) {
        // add current count to total
        cnt = cnt + counts[i].count;
    }
    // return total count
    return { count:cnt };
}