package com.fl0w3r.core.hydro.ui.overview

data class Article(
    val articleId: Int,
    val articleUrl: String,
    val articleImage: String,
    val articleTitle: String,
    val articleDescription: String
)

val ARTICLE_LIST = listOf(
    Article(
        articleId = 1,
        articleUrl = "https://www.medicalnewstoday.com/articles/290814",
        articleImage = "https://cdn-prod.medicalnewstoday.com/content/images/articles/290/290814/benefits-of-drinking-water.jpg",
        articleTitle = "Fifteen benefits of drinking water",
        articleDescription = "Around 60 percent of the body is made up of water, and around 71 percent of the planet’s surface is covered by water.Perhaps it is the ubiquitous nature of water that means drinking enough each day is not at the top of many people’s lists of priorities.",
    ),
    Article(
        articleId = 2,
        articleUrl = "https://health.ucdavis.edu/blog/good-food/why-its-important-for-you-to-drink-water-and-stay-hydrated/2022/07",
        articleImage = "https://health.ucdavis.edu/assets/images/blog/marquee/good-food-good-medicine-marquee.jpg",
        articleTitle = "Why should I drink water?",
        articleDescription = "As the weather heats up this summer, you might be wondering what drink will keep you hydrated. Water allows you to stay hydrated and doesn’t contain unnecessary calories or additives. Our registered dietitian team shares why water is your best choice year-round.",
    ),
    Article(
        articleId = 3,
        articleUrl = "https://www.who.int/news/item/29-09-2022-rolling-revision-of-the-guidelines-for-drinking-water-quality",
        articleImage = "https://journals.physiology.org/pb-assets/images/journal_header_image/article_header_image_ajpregu.jpg",
        articleTitle = "Drink at least eight glasses of water a day.",
        articleDescription = "Despite the seemingly ubiquitous admonition to “drink at least eight 8-oz glasses of water a day” (with an accompanying reminder that beverages containing caffeine and alcohol do not count), rigorous proof for this counsel appears to be lacking.",
    ),
    Article(
        articleId = 4,
        articleUrl = "https://www.everydayhealth.com/water-health/water-body-health.aspx",
        articleImage = "https://images.everydayhealth.com/images/health-benefits-of-water-backed-by-scientific-research-1440x810.jpg?w=1110",
        articleTitle = "7 Health Benefits of Water",
        articleDescription = "You know you need water to survive, and you feel better when you drink it regularly. But what’s really at play in the body when you sip H2O?",
    ),
)