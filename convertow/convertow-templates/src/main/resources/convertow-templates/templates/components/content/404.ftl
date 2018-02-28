[#include "/convertow-templates/templates/macros/link-macro.ftl"]

[@assignLinkParameters content "linkType"/]

<section class="matrl-error-section section-padding">
    <div class="container">
        <div class="row">
            <div class="col-md-12 text-center">
                <h1 class="wow animated fadeInRight" data-wow-delay=".2s">${i18n['notfaund.code']}</h1>
                <h2 class="wow animated fadeInRight" data-wow-delay=".4s">${i18n['notfaund.label']}</h2>
                <a href="${linkHref!"#"}" class="wow animated fadeInUp btn btn-common mt-5" data-wow-delay=".6s"><i class="material-icons mdi mdi-home"></i> ${i18n['notfaund.home']}<div class="ripple-container"></div></a>
            </div>
        </div>
    </div>
</section>