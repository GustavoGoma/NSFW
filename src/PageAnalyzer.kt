import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class PageAnalyzer(url: String) {

    val TAG = "PageAnalyzer"
    var videos: JSONArray

    init {
        println("$TAG:init")
        val doc = Jsoup.connect(url).get()

        this.videos = JSONArray()

            getThumbnailsDataFromHTML(doc.select("div.thumb-block"))
    }

    private fun getThumbnailsDataFromHTML(thumbnails: Elements){
        println("$TAG:getThumbnailsDataFromHTML")
        for (thumbnail in thumbnails) {
            val item = JSONObject()
            item.put("id", thumbnail.attr("id"))


            val links = thumbnail.select("a[title]")
            for (link in links) {
                item.put("url", link.attr("href"))
                item.put("title", link.attr("title"))
            }

            val lengths = thumbnail.select("p.metadata span strong")
            for (length in lengths){
                item.put("length", length.text())
            }

//            val imgs = thumbnail.select("div.thumb")
//            for (img in imgs){
//                println(img.toString())
//            }

            this.videos.put(item)
        }
    }

    public fun toJSON(): JSONObject {
        val json = JSONObject()

        if (videos.length() > 0) json.put("thumbnails", videos)

        return json
    }

    override fun toString(): String {
        return toJSON().toString(2)
    }
}