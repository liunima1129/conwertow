[#assign image = damfn.getAssetLink(content.image!)!]

<div class="col-md-6 col-lg-3 col-xl-3 wow animated fadeInUp" data-wow-delay=".3s">
    <div class="single-team-widget">
        <img src="${image!}" class="img-fluid" alt="${content.name!}">
        <div class="team-member-info">
            <h2 class="subtitle">${content.name!}</h2>
            <p>${content.role!}</p>
            <div class="social-profiles">
                [#if content.twitterLink?has_content]
                    <a href="${content.twitterLink!}"><i class="mdi mdi-twitter"></i></a>
                [/#if]
                [#if content.facebookLink?has_content]
                    <a href="${content.facebookLink!}"><i class="mdi mdi-facebook"></i></a>
                [/#if]
                [#if content.linkedinkLink?has_content]
                    <a href="${content.linkedinkLink!}"><i class="mdi mdi-linkedin"></i></a>
                [/#if]
                [#if content.instagramkLink?has_content]
                    <a href="${content.instagramkLink!}"><i class="mdi mdi-instagram"></i></a>
                [/#if]
            </div>
        </div>
    </div>
</div>