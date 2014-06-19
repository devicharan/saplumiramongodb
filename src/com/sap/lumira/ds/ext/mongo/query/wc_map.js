function map() {
    // get content of the current document
    var cnt = this.content;
    // split the content into an array of words using a regular expression
    var words = cnt.match(/\w+/g);
    // if there are no words, return
    if (words == null) {
        return;
    }
    // for each word, output {word, count} pair
    for (var i = 0; i < words.length; i++) {
        emit({ word:words[i] }, { count:1 });
    }
}