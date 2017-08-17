import org.json.JSONObject

class Searcher(mainURL: String, keyWords: String) {

    val TAG = "Searcher"
    var videos: JSONObject

    init {
        println("$TAG:init")

        val encodedKeyWorlds = encodeKeyWords(keyWords)
        val url = mainURL + "?k=" + encodedKeyWorlds
        val page = PageAnalyzer(url)
        videos = page.toJSON()
    }

    private fun encodeKeyWords(keyWords: String): String{
        var encoded = keyWords
        var index = encoded.indexOf(' ')
        while (index != -1){
            encoded = encoded.replace(' ', '+')
            index = encoded.indexOf(' ')
        }

        return encoded
    }

}