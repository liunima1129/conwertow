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