# MarkdownView
Markdown View provides a highly customisable markdown library for Android.
You can customise the various tags for the Markdown, and even provide CSS for the HTML content created as an intermediate to customise the experience of the markdown converted.  
The library is still in its nacent stage, so please support so I can put in more time and effort in bringing this forward.

Jcenter Gradle integration coming soon. For the time being, .aar file added in the aar folder, which can be included as follows
```
repositories {
    flatDir {
        dirs 'libs'
    }
}

compile(name:'markdownlib', ext:'aar')
```
