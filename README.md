# News App with Jetpack Compse

## Overview
This is a news app built with Jetpack Compose. The app consists of three main pages:
1. **Headline Page**: Displays news categories, a banner header, and a paginated list of the latest news articles.
2. **Bookmark Page**: Shows a list of news articles that have been bookmarked. The data is sourced from a local Room database.
3. **Detail Page**: Provides detailed information about a selected news article, including an image, title, description, content, author, and more. It also includes a bookmark button that allows users to add or remove the article from their bookmarks.

## Features

### Headline Page
- **Categories**: Browse news by different categories.
- **Banner Header**: A prominent banner showcasing top news.
- **Latest News List**: A scrollable, paginated list of the most recent news articles, implemented with Paging 3.

### Bookmark Page
- **Bookmarked News List**: A list of articles that have been bookmarked by the user. The data is stored and retrieved from a local Room database.

### Detail Page
- **News Details**: Detailed view of a selected news article, including:
  - Image
  - Title
  - Description
  - Content
  - Author
- **Bookmark Button**: Allows users to bookmark or remove the article from bookmarks:
  - If the article is bookmarked, the button has a red background and the title "Remove from Bookmark".
  - If the article is not bookmarked, the button has a black background and the title "Add to Bookmark".
  - The button functionality includes adding or deleting the article from the local Room database.

## Screenshots
<p align="center">
    <img src="https://drive.usercontent.google.com/download?id=1HUtWGcwmxm4wOjhKgAspP9buZOFBi9G6" alt="Headline Page" width="200" style="padding: 50px;"/>
    <img src="https://drive.usercontent.google.com/download?id=1HYtGXMS389LP2cBvHWxUByfQchm3mN_M" alt="Bookmark Page" width="200" style="padding: 50px;"/>
    <img src="https://drive.usercontent.google.com/download?id=1H_yIZta_99vlWW4erCRlDRHtJl-H_wlY" alt="Detail Page" width="200" style="padding: 50px;"/>
    <img src="https://drive.usercontent.google.com/download?id=1Hbr5EwE37JFtdg-jKBcnfdWl0IdbFyrD" alt="News Details" width="200" style="padding: 50px;"/>
    <img src="https://drive.usercontent.google.com/download?id=1HjQ2BPAwTOUwHghGhLBSd9MNuLtN4VYh" alt="Bookmark Button - Add" width="200" style="padding: 50px;"/>
    <img src="https://drive.usercontent.google.com/download?id=1HlUvxV-wkEkccI-o59cdfRR73us0zOf6" alt="Bookmark Button - Remove" width="200" style="padding: 50px;"/>
</p>


## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/mShofwan17/news-app-compose
