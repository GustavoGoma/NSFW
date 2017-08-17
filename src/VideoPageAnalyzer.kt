import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class VideoPageAnalyzer(var mainURL: String, var url: String) {

    val TAG = "VideoPageAnalyzer"
    var video: JSONObject

    init {
        println("$TAG:init")
        val doc = Jsoup.connect(mainURL + url.substring(1)).get()

        this.video = JSONObject()

        val scripts = doc.select("script")
        getVideoUploader(doc.select("a.hg")[0])
        getVideoRatingData(
                doc.select("span.total")[0],
                doc.select("strong#nb-views-number")[0])
        getVideoTags(doc.select("a.nu"))
//            getThumbnailsDataFromScript(scripts[3])
        getVideoLinks(scripts[7])
    }

    private fun getVideoUploader(element: Element) {
        val json = JSONObject()
        json.put("url", element.attr("href"))
        json.put("name", element.text())

        video.put("uploader", json)
    }

    private fun getVideoRatingData(likes: Element, views: Element) {
        val json = JSONObject()
        json.put("likes", likes.text())
        json.put("views", views.text())

        video.put("rating", json)
    }
    private fun getVideoLinks(script: Element) {
        println("$TAG:getVideoLinks")

        fun getLink(scriptLine: String): String {
            return scriptLine.substring(scriptLine.indexOf("(\'") + 2, scriptLine.length - 3)
        }

        val lines = script.data().split("\n")

        val urls = JSONObject()
        urls.put("low_quality_video", getLink(lines[6]))
        urls.put("high_quality_video", getLink(lines[7]))
        urls.put("thumbnail", getLink(lines[9]))
        urls.put("thumbnail169", getLink(lines[10]))

        video.put("title", getLink(lines[4]))
        video.put("urls", urls)
    }

    private fun getVideoTags(tags: Elements) {
        val array = JSONArray()
        for (c in tags) {
            array.put(c.text())
        }
        video.put("tags", array)
    }

    public fun toJSON(): JSONObject {
        val json = JSONObject()

        if (video.length() > 0) json.put("video", video)

        return json
    }

    override fun toString(): String {
        return toJSON().toString(2)
    }
}