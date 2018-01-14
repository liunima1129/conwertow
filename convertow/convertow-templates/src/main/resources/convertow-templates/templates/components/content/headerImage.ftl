[#assign image = damfn.getAssetLink(content.image!)!]

<section class="page-title-section section-padding" style="background-image: url(${image!})">
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <h2 class="page-tagline">${content.title!}</h2>
                <h1 class="page-title">${content.subtitle!}</h1>
            </div>
        </div>
    </div>
</section>