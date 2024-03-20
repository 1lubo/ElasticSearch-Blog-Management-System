<#import "../common/standardPage.ftl" as p>
<@p.page title="${(article.title)!'New Post'}">
  <script type="text/javascript" src="/webjars/ckeditor/4.4.7/standard/ckeditor.js"></script>
  <!-- Post Content Column -->
  <div class="col-lg-8">
    <form action="/article" method="post">
      <#if article?? >
        <input type="hidden" id="id" name="id" value="${article.id}"/>
        <div class="mb-3">
          <label for="postTitle">Title</label>
          <input type="text" class="form-control" id="title" name="title" placeholder="Post Title"
                 value="${article.title}" required="true">
        </div>
        <div class="mb-3">
          <label for="postLink">Permalink</label>
          <input type="text" class="form-control" id="link" name="link" placeholder="Post Perma Link"
                 value="${article.link}" required="true">
        </div>
        <div class="mb-3">
          <label for="postSummary">Summary</label>
          <textarea class="form-control" id="summary" name="summary" rows="3" required="true">
              ${article.summary}
          </textarea>
        </div>
        <div class="mb-3">
          <label for="postBody">Body</label>
          <textarea class="form-control" id="body" name="body" rows="10" required="true">
            ${article.body}
          </textarea>
        </div>
      <#else>
        <div class="mb-3">
          <label for="postTitle">Title</label>
          <input type="text" class="form-control" id="title" name="title" placeholder="Post Title" required="true">
        </div>
        <div class="mb-3">
          <label for="postLink">Permalink</label>
          <input type="text" class="form-control" id="link" name="link" placeholder="Post Perma Link" required="true">
        </div>
        <div class="mb-3">
          <label for="postSummary">Summary</label>
          <textarea class="form-control" id="summary" name="summary" rows="3" required="true"></textarea>
        </div>
        <div class="mb-3">
          <label for="postBody">Body</label>
          <textarea class="form-control" id="body" name="body" rows="10" required="true"></textarea>
        </div>
      </#if>
      <div class="mb-3">
        <input class="form-control btn btn-primary" type="submit" value="Save"/>
      </div>
      <script type="text/javascript">
        CKEDITOR.replace( 'body');

        $("#title").keyup(function (){
            let str = $(this).val();
            str = str.replace(/[^a-zA-Z0-9\s]/g,"");
            str = str.toLowerCase();
            str = str.replace(/\s/g, '-');
            $("#link").val(str);
        });
      </script>
    </form>
  </div>
</@p.page>