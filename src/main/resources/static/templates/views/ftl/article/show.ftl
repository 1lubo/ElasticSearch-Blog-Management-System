<#import "../common/standardPage.ftl" as p>
<#if article??>
  <@p.page title="${article.title}">
    <!-- Post Content Column -->
    <div class="col-lg-8">
      <!-- Title -->
      <h1 class="mt-4">${article.title}</h1>
      <!-- Author -->
       <p class="lead">
        by
       <#if article.author??>
        <a href="#">${article.author.username}</a>
       <#else>
        Anonymous
       </#if>
       </p>
       <!-- Date/Time -->
       <p>${article.createdDate?string('dd.MM.yyy HH:mm')}</p>
       <hr>
       <!-- Post Content -->
       ${article.body}
       <hr>
       <#if article.author?? && user??>
        <#if article.author.username == user.username ||
          user.role == "ADMIN" >
          <form id="form_delete_${article.id}" method="post" action="/article/show/delete/${article.id}"></form>
            <p>
              <a class="btn btn-success" href="/article/show/edit/${article.link}">Edit</a>
              <a href="#" class="btn btn-danger" onclick="$('#form_delete_${article.id}').submit();">Delete</a>
             </p>
        </#if>
       </#if>
    </div>
  </@p.page>
</#if>