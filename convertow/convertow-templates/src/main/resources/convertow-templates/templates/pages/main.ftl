[#-------------- RENDERING --------------]
<!DOCTYPE html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><![endif]-->
<!--[if gt IE 8]><!--><html class="no-js" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><!--<![endif]-->
<head>
[@cms.page /]

    [@cms.area name="htmlHeader" contextAttributes={"pageDef":def} /]
</head>
<body>

[@cms.area name="navigation"/]
[@cms.area name="carousel"/]
[@cms.area name="main"/]
[@cms.area name="footer"/]

<a href="#" class="back-to-top">
    <div class="ripple-container"></div>
    <i class="mdi mdi-arrow-up"></i>
</a>

<div id="preloader">
    <div class="loader" id="loader-1"></div>
</div>

[#if def.jsFiles??]
    [#list def.jsFiles as jsFile]
    <script src="${jsFile.link}"></script>
    [/#list]
[/#if]
<script src="${ctx.contextPath}/.resources/convertow-templates/webresources/js/fileupload/jquery.ui.widget.min.js"></script>
<script src="${ctx.contextPath}/.resources/convertow-templates/webresources/js/fileupload/jquery.iframe-transport.js"></script>
<script src="${ctx.contextPath}/.resources/convertow-templates/webresources/js/fileupload/jquery.fileupload.js"></script>
<script>
    var contextPath = "${ctx.contextPath!}";
    $(document).ready(function(){
        $('.dropify-wrapper').each(function () {
            $(this).fileupload({
                url: contextPath + '/fileUpload',
                dataType: 'json',
                limitConcurrentUploads: 1,
                limitMultiFileUploads: 1,
                dropZone: $(this),
                done: function (e, data) {

                },
                add: function (e, data) {
                    if (e.isDefaultPrevented()) {
                        return false;
                    }
                    var goUpload = true;
                    $("body").addClass("loading");
                    var uploadFile = data.files[0];
                    if (goUpload) {
                        if (data.autoUpload || (data.autoUpload !== false && $(this).fileupload('option', 'autoUpload'))) {
                            data.process().done(function () {
                                data.submit();
                            });
                        }
                    }
                },
                error: function (e, data) {
                    $("body").removeClass("loading");
                },
                success: function (resp) {
                    $("body").removeClass("loading");
                    var zipLink = resp.uploadDir + resp.fileName + ".zip";
                    $('.download-all a').attr('href', zipLink);
                    $('.download-all a').removeClass("no-active");
                    for(var i = 1; i < resp.pageNum; i++) {
                        var link = resp.uploadDir + resp.fileName;
                        link = link + "_" + i + ".jpg";

                        var tr = $('<tr/>').addClass("template-upload fade in").appendTo('.files');

                        var tdImage = $('<td/>').appendTo(tr);
                        tdImage.css("width", "60%");
                        var tdName = $('<td/>').appendTo(tr);
                        tdName.css("width", "25%");
                        tdName.css("padding", "3%");
                        var tdLink = $('<td/>').appendTo(tr);
                        tdLink.css("width", "15%");
                        tdLink.css("text-align", "right");
                        tdLink.css("padding", "3%");

                        var img = $('<img/>').appendTo(tdImage);
                        img.attr('src', link);
                        img.css("width", "10%");

                        var p = $('<p/>').appendTo(tdName);
                        p.text(resp.fileName);

                        $(tdLink).append("<a class='separate-file-link' target='_blank' href='" + link + "'>Open "+ i +"</a>");

                        /*var div = $('<div/>').addClass("res").appendTo('.separated-files');
                        var img = $('<img/>').appendTo(div);
                        img.attr('src', link);
                        img.css("width", "10%");
                        $(div).append("<a class='separate-file-link' target='_blank' href='" + link + "'>Image "+ i +"</a><div class='clearfix h-10'></div>");*/
                    }
                }
            });
        });
    });
</script>
</body>
</html>
