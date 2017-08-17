enum class Urls(var desc: String, var url: String) {
    HOME_PAGE("Home page", "https://xvideos.com/"),
    VIDEO_PAGE("Video page", "/video28275051/eletrifying_guys_schlong")
}

fun main(args: Array<String>){
    println("Main:main")

    var url = Urls.HOME_PAGE
    val homePage = PageAnalyzer(url.url)
    println("${url.desc}\n  $homePage")

    url = Urls.VIDEO_PAGE
    val videoPage = VideoPageAnalyzer(Urls.HOME_PAGE.url, url.url)
    println("${url.desc}\n  $videoPage")

    var search = Searcher(Urls.HOME_PAGE.url, "brazzers")
}