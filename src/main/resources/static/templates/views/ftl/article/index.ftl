<#import "../common/standardPage.ftl" as p>
<@p.page title="Posts">
  <!-- Post Content Column -->
<#--  <div class="row">-->
    <!-- Blog Entries -->
    <div class="col-lg-8">
        <#if articles??>
            <#if pageNumber < 1 >
                <#assign featured = articles.content[0] nonfeatured = articles.content[1..(articles.content?size-1)]>
                <!-- Featured article -->
                <div class="card mb-4">
                <div class="card-body">
                  <div class="small text-muted">posted on ${featured.createdDate?string('dd.MM.yyyy')}</div>
                  <!-- Title -->
                  <h2 class="card-title">${featured.title}</h2>
                  <p class="lead">
                    by
                      <#if featured.author??>
                        <a href="#">${featured.author.username}</a>
                      <#else>
                        Anonymous
                      </#if>
                  </p>
                  <!-- Summary -->
                  <p class="card-text">${featured.summary}</p>
                  <a class="btn btn-primary" href="article/show/${featured.link}">Read more &#8594;</a>
                </div>
              </div>
                <!-- Nested row for non-featured blog posts-->
                <div class="row">
                <#list nonfeatured as nonFeaturedarticle>
                    <!-- Blog post-->
                    <div class="col-lg-6">
                      <div class="card mb-4">
                        <div class="card-body">
                          <!-- Date/Time -->
                          <div class="small text-muted">posted on ${nonFeaturedarticle.createdDate?string('dd.MM.yyyy')}</div>
                          <!-- Title -->
                          <h2 class="card-title">${nonFeaturedarticle.title}</h2>
                          <a href="article/show/${nonFeaturedarticle.link}"></a>
                          <!-- Author -->
                          <p class="lead">
                            by
                              <#if nonFeaturedarticle.author??>
                                <a href="#">${nonFeaturedarticle.author.username}</a>
                              <#else>
                                Anonymous
                              </#if>
                          </p>
                          <!-- Summary -->
                          <p class="card-text">${nonFeaturedarticle.summary}</p>
                          <a class="btn btn-primary" href="article/show/${nonFeaturedarticle.link}">Read more &#8594;</a>
                        </div>
                      </div>
                    </div>
                  </#list>
              </div>
            <#else>
              <!-- Row for blog posts-->
              <div class="row">
                  <#list articles.content as article>
                    <!-- Blog post-->
                    <div class="col-lg-6">
                      <div class="card mb-4">
                        <div class="card-body">
                          <!-- Date/Time -->
                          <div class="small text-muted">posted on ${article.createdDate?string('dd.MM.yyyy')}</div>
                          <!-- Title -->
                          <h2 class="card-title">${article.title}</h2>
                          <a href="article/show/${article.link}"></a>
                          <!-- Author -->
                          <p class="lead">
                            by
                              <#if article.author??>
                                <a href="#">${article.author.username}</a>
                              <#else>
                                Anonymous
                              </#if>
                          </p>
                          <!-- Summary -->
                          <p class="card-text">${article.summary}</p>
                          <a class="btn btn-primary" href="article/show/${article.link}">Read more &#8594;</a>
                        </div>
                      </div>
                    </div>
                  </#list>
              </div>
            </#if>
          <!-- Page Navigation -->
          <nav aria-label="Page navigation">
            <hr class="my-0">
            <ul class="pagination justify-content-center my-4">
                <#if articles.hasPrevious()>
                  <li class="page-item"><a class="page-link" href="article?page=#{articles.previousPageable()
                      .pageNumber}&size=10">Previous</a></li>
                </#if>
                <#list 1..articles.totalPages as i>
                  <li class="page-item"><a class="page-link" href="article?page=${i-1}&size=10">${i}</a></li>
                </#list>
                <#if articles.hasNext()>
                  <li class="page-item"><a class="page-link" href="article?page=${articles.nextPageable()
                      .pageNumber}&size=10">Next</a></li>
                </#if>
            </ul>
          </nav>
        </#if>
    </div>
<#--  </div>-->
</@p.page>