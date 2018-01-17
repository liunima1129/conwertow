<section class="wrapper">
    <div class="container">
        <div class="row">

            <div class="col-md-12">
                <div class="alert alert-dismissible alert-danger">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <h4>${i18n['file.extension.not.supported']}!</h4>
                    <p>${i18n['file.extension.not.supported.message']}
                        <a href="javascript:void(0)" class="alert-link">${i18n['extension.advice']} <span class="error-extension"></span> ${i18n['extension']}</a>.</p>
                </div>
            </div>

            <div class="col-md-12">
                <div class="alert alert-dismissible alert-success">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <h4>${i18n['success']}</h4>
                    <p>${i18n['convert.success']}
                        <a href="javascript:void(0)" class="alert-link">${i18n['convert.success.download']}</a>.</p>
                </div>
            </div>

            <div class="col-md-12">
                <div class="alert alert-dismissible alert-info">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <h4>${i18n['info']}</h4>
                    <p>${i18n['info.message']}
                        <a href="javascript:void(0)" class="alert-link">${i18n['info.convert']}</a>.</p>
                </div>
            </div>

            <div class="col-md-12">
                <div class="dropify-wrapper">

                    <div class="dropify-message">
                        <i class="material-icons pulse-shrink mdi mdi-file"></i>
                        <p>${i18n['drag.and.drop']}</p>
                        <p class="dropify-error">${i18n['large.file']}</p>
                    </div>
                    <input type="hidden" name="userID" id="userID" class="userID" value="">
                    <input type="hidden" name="fileName" id="fileName" class="fileName" value="">
                    <input type="file" id="input-file-max-fs" class="dropify" data-max-file-size="2M">
                    <button type="button" class="dropify-clear">${i18n['remove.label']}</button>
                    <button type="button" class="dropify-convert">${i18n['convert.label']}</button>

                    <div class="dropify-preview">
                        <span class="dropify-render">
                            <i class="material-icons pulse-shrink mdi "></i>
                            <span class="dropify-extension"></span>
                        </span>
                        <div class="dropify-infos">
                            <div class="dropify-infos-inner">
                                <p class="dropify-filename">
                                    <span class="file-icon"></span>
                                    <span class="dropify-filename-inner"></span>
                                </p>
                                <p class="dropify-infos-message">${i18n['drag.and.drop.success']}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-2 zip-div">
                <a href="javascript:void(0)" class="animated4 btn btn-common btn-lg zip-archive"><i class="material-icons mdi mdi-cloud-download"></i> ${i18n['download']}<div class="ripple-container"></div></a>
            </div>
        </div>
    </div>
</section>

<style>
    /*!
 * =============================================================
 * dropify v0.2.0 - Override your input files with style.
 * https://github.com/JeremyFagis/dropify
 *
 * (c) 2015 - Jeremy FAGIS <jeremy@fagis.fr> (http://fagis.fr)
 * =============================================================
 */
    .alert,footer,.zip-div{
        margin-top: 1rem;
    }
    .zip-archive{
        display: none;
    }
    .alert-danger, .alert-success, .alert-info{
        display: none;
        margin-bottom: 0px!important;
    }
    @font-face {
        font-family: dropify;
        src: url(../fonts/dropify.eot);
        src: url(../fonts/dropify.eot#iefix) format('embedded-opentype'),url(../fonts/dropify.woff) format('woff'),url(../fonts/dropify.ttf) format('truetype'),url(../fonts/dropify.svg#dropify) format('svg');
        font-weight: 400;
        font-style: normal
    }

    .dropify-font:before,.dropify-wrapper .dropify-message span.file-icon:before,.dropify-wrapper .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-filename span.file-icon:before,[class*=" dropify-font-"]:before,[class^=dropify-font-]:before {
        font-family: dropify;
        font-style: normal;
        font-weight: 400;
        speak: none;
        display: inline-block;
        text-decoration: inherit;
        width: 1em;
        margin-left: .2em;
        margin-right: .2em;
        text-align: center;
        font-variant: normal;
        text-transform: none;
        line-height: 1em
    }

    .dropify-wrapper,.dropify-wrapper .dropify-clear ,.dropify-wrapper .dropify-convert{
        font-family: Roboto,"Helvetica Neue",Helvetica,Arial
    }

    .dropify-font-upload:before,.dropify-wrapper .dropify-message span.file-icon:before {
        content: '\e800'
    }

    .dropify-font-file:before {
        content: '\e801'
    }

    .dropify-wrapper {
        margin-top: 1rem;
        display: block;
        position: relative;
        cursor: pointer;
        overflow: hidden;
        width: 100%;
        max-width: 100%;
        height: 200px;
        padding: 5px 10px;
        font-size: 14px;
        line-height: 22px;
        color: #777;
        background-color: #FFF;
        background-image: none;
        text-align: center;
        border: 2px solid #E5E5E5
    }

    .dropify-wrapper:hover {
        background-size: 30px 30px;
        background-image: -webkit-linear-gradient(135deg,#F6F6F6 25%,transparent 25%,transparent 50%,#F6F6F6 50%,#F6F6F6 75%,transparent 75%,transparent);
        background-image: linear-gradient(-45deg,#F6F6F6 25%,transparent 25%,transparent 50%,#F6F6F6 50%,#F6F6F6 75%,transparent 75%,transparent);
        -webkit-animation: stripes 2s linear infinite;
        animation: stripes 2s linear infinite
    }

    .dropify-wrapper.has-error .dropify-message .dropify-error,.dropify-wrapper.has-preview .dropify-clear,.dropify-wrapper.has-preview .dropify-convert{
        display: block
    }

    .dropify-wrapper.disabled input {
        cursor: not-allowed
    }

    .dropify-wrapper.disabled:hover {
        background-image: none;
        -webkit-animation: none;
        animation: none
    }

    .dropify-wrapper.disabled .dropify-message {
        opacity: .5;
        text-decoration: line-through
    }

    .dropify-wrapper.disabled .dropify-infos-message {
        display: none
    }

    .dropify-wrapper input {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        height: 100%;
        width: 100%;
        opacity: 0;
        cursor: pointer;
        z-index: 5
    }

    .dropify-wrapper .dropify-message {
        position: relative;
        top: 50%;
        -webkit-transform: translateY(-50%);
        -ms-transform: translateY(-50%);
        transform: translateY(-50%)
    }

    .dropify-wrapper .dropify-message span.file-icon {
        font-size: 50px;
        color: #CCC
    }

    .dropify-wrapper .dropify-message p {
        margin: 5px 0 0
    }

    .dropify-wrapper .dropify-message p.dropify-error {
        color: #F34141;
        font-weight: 700;
        display: none
    }

    .dropify-wrapper .dropify-clear,.dropify-wrapper .dropify-convert {
        display: none;
        position: absolute;
        opacity: 0;
        z-index: 7;
        top: 10px;
        right: 10px;
        background: 0 0;
        border: 2px solid #FFF;
        text-transform: uppercase;
        font-size: 11px;
        padding: 4px 8px;
        font-weight: 700;
        color: #FFF;
        -webkit-transition: all .15s linear;
        transition: all .15s linear
    }

    .dropify-wrapper .dropify-clear:hover,.dropify-wrapper .dropify-convert:hover {
        background: rgba(255,255,255,.2)
    }

    .dropify-wrapper .dropify-preview {
        display: none;
        position: absolute;
        z-index: 1;
        background-color: #FFF;
        padding: 5px;
        width: 100%;
        height: 100%;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        overflow: hidden;
        text-align: center
    }

    .dropify-wrapper .dropify-preview .dropify-render img {
        top: 50%;
        -webkit-transform: translate(0,-50%);
        -ms-transform: translate(0,-50%);
        transform: translate(0,-50%);
        position: relative;
        max-width: 100%;
        max-height: 100%;
        background-color: #FFF;
        -webkit-transition: border-color .15s linear;
        transition: border-color .15s linear
    }

    .dropify-wrapper .dropify-preview .dropify-render i {
        font-size: 70px;
        top: 50%;
        left: 50%;
        -webkit-transform: translate(-50%,-50%);
        -ms-transform: translate(-50%,-50%);
        transform: translate(-50%,-50%);
        position: absolute;
        color: #777
    }

    .dropify-wrapper .dropify-preview .dropify-render .dropify-extension {
        position: absolute;
        top: 50%;
        left: 50%;
        -webkit-transform: translate(-50%,-50%);
        -ms-transform: translate(-50%,-50%);
        transform: translate(-50%,-50%);
        margin-top: 10px;
        text-transform: uppercase;
        font-weight: 900;
        letter-spacing: -.03em;
        font-size: 13px;
        width: 42px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis
    }

    .dropify-wrapper .dropify-preview .dropify-infos {
        position: absolute;
        left: 0;
        top: 0;
        right: 0;
        bottom: 0;
        z-index: 3;
        background: rgba(0,0,0,.7);
        opacity: 0;
        -webkit-transition: opacity .15s linear;
        transition: opacity .15s linear
    }

    .dropify-wrapper .dropify-preview .dropify-infos .dropify-infos-inner {
        position: absolute;
        top: 50%;
        -webkit-transform: translate(0,-40%);
        -ms-transform: translate(0,-40%);
        transform: translate(0,-40%);
        -webkit-backface-visibility: hidden;
        backface-visibility: hidden;
        width: 100%;
        padding: 0 20px;
        -webkit-transition: all .2s ease;
        transition: all .2s ease
    }

    .dropify-wrapper .dropify-preview .dropify-infos .dropify-infos-inner p {
        padding: 0;
        margin: 0;
        position: relative;
        width: 100%;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: #FFF;
        text-align: center;
        line-height: 25px;
        font-weight: 700
    }

    .dropify-wrapper .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-filename span.file-icon {
        margin-right: 2px
    }

    .dropify-wrapper .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-infos-message {
        margin-top: 15px;
        padding-top: 15px;
        font-size: 12px;
        position: relative;
        opacity: .5
    }

    .dropify-wrapper .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-infos-message::before {
        content: '';
        position: absolute;
        top: 0;
        left: 50%;
        -webkit-transform: translate(-50%,0);
        -ms-transform: translate(-50%,0);
        transform: translate(-50%,0);
        background: #FFF;
        width: 30px;
        height: 2px
    }

    .dropify-wrapper:hover .dropify-clear,.dropify-wrapper:hover .dropify-convert,.dropify-wrapper:hover .dropify-preview .dropify-infos {
        opacity: 1
    }

    .dropify-wrapper:hover .dropify-preview .dropify-infos .dropify-infos-inner {
        margin-top: -5px
    }

    .dropify-wrapper.touch-fallback {
        height: auto!important
    }

    .dropify-wrapper.touch-fallback:hover {
        background-image: none;
        -webkit-animation: none;
        animation: none
    }

    .dropify-wrapper.touch-fallback .dropify-preview {
        position: relative;
        padding: 0
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-render {
        display: block;
        position: relative
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-infos-message::before,.dropify-wrapper.touch-fallback.has-preview .dropify-message {
        display: none
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-render .dropify-font-file {
        position: relative;
        -webkit-transform: translate(0,0);
        -ms-transform: translate(0,0);
        transform: translate(0,0);
        top: 0;
        left: 0
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-render .dropify-font-file::before {
        margin-top: 30px;
        margin-bottom: 30px
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-render img {
        position: relative;
        -webkit-transform: translate(0,0);
        -ms-transform: translate(0,0);
        transform: translate(0,0)
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-infos {
        position: relative;
        opacity: 1;
        background: 0 0
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-infos .dropify-infos-inner {
        position: relative;
        top: 0;
        -webkit-transform: translate(0,0);
        -ms-transform: translate(0,0);
        transform: translate(0,0);
        padding: 5px 90px 5px 0
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-infos .dropify-infos-inner p {
        padding: 0;
        margin: 0;
        position: relative;
        width: 100%;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: #777;
        text-align: left;
        line-height: 25px
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-filename {
        font-weight: 700
    }

    .dropify-wrapper.touch-fallback .dropify-preview .dropify-infos .dropify-infos-inner p.dropify-infos-message {
        margin-top: 0;
        padding-top: 0;
        font-size: 11px;
        position: relative;
        opacity: 1
    }

    .dropify-wrapper.touch-fallback .dropify-message {
        -webkit-transform: translate(0,0);
        -ms-transform: translate(0,0);
        transform: translate(0,0);
        padding: 40px 0
    }

    .dropify-wrapper.touch-fallback .dropify-clear,.dropify-wrapper.touch-fallback .dropify-convert {
        top: auto;
        bottom: 23px;
        opacity: 1;
        border-color: rgba(119,119,119,.7);
        color: #777
    }

    .dropify-wrapper.touch-fallback:hover .dropify-preview .dropify-infos .dropify-infos-inner {
        margin-top: 0
    }

    @-webkit-keyframes stripes {
        from {
            background-position: 0 0
        }

        to {
            background-position: 60px 30px
        }
    }

    @keyframes stripes {
        from {
            background-position: 0 0
        }

        to {
            background-position: 60px 30px
        }
    }
    .dropify-extension{
        margin-top: 40px!important;
    }
    .dropify-convert{
        bottom: 10px!important;
        top: unset!important;
    }
    .dropify-convert,.dropify-clear{
        width: 100px;
    }
</style>


