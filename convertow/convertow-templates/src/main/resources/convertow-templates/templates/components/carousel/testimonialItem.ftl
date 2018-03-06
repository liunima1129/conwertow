<div class="item">
    <div class="testimonial-item">
        <div class="img">
            <a href="#">
                <#--<img class="img-fluid" src="${damfn.getAssetLink(content.image!)}" alt="${content.userName!}">-->
                <img class="lazy img-fluid" data-src="${damfn.getAssetLink(content.image!)}" alt="${content.userName!}"/>
            </a>
        </div>
        <div class="datils">
            <h5>${content.userName!}</h5>
            <span>${content.role!}</span>
            <p>${content.reference!}</p>
        </div>
    </div>
</div>