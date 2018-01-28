[#-------------- RENDERING --------------]
<!DOCTYPE html>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><![endif]-->
<!--[if gt IE 8]><!--><html [#if cmsfn.language() == "ar"]dir="rtl"[/#if] class="no-js" xml:lang="${cmsfn.language()}" lang="${cmsfn.language()}"><!--<![endif]-->
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

        $(".dropify-convert").click(function (e) {

            $(".alert-danger").hide();
            $(".alert-success").hide();
            $(".zip-archive").hide();

            $.ajax({
                type: "GET",
                dataType: "json",
                url: restPath + $(".userID").val() +"&name=" + $(".fileName").val(),
                success: function(e, data){
                    $(".alert-success").show();
                    $(".zip-archive").show();
                    $(".alert-info").hide();
                },
                error: function (e, data) {
                    $(".alert-info").hide();
                    $(".alert-danger").hide();
                    $(".zip-archive").hide();
                }
            });
        });

        $(".dropify-clear").click(function (e) {
            $(".dropify-wrapper").removeClass("has-preview");
            $(".dropify-preview").css("display","none");
        });

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

                    var uploadFile = data.files[0];
                    var extension = uploadFile.name.split('.').pop();

                    if( fileSupported.toLowerCase() != extension && fileSupported. toUpperCase() != extension ){
                        goUpload = false;
                        $(".error-extension").text(fileSupported);
                        $(".alert-danger").css("display", "block");
                    }

                    if( extension == "tiff" || extension == "TIFF" || extension == "jpg" || extension == "JPG" || extension == "png" || extension == "PNG" || extension == "svg" || extension == "SVG"  ){
                        $(".dropify-render i.material-icons").addClass("mdi-image");
                    }else if( extension == "pdf" || extension == "PDF"){
                        $(".dropify-render i.material-icons").addClass("mdi-file-pdf");
                    }


                    $(".dropify-extension").text(extension);
                    $(".dropify-filename-inner").text(uploadFile.name);

                    if (goUpload) {

                        $(".fileName").val(uploadFile.name);
                        $(".alert-danger").hide();
                        $(".alert-info").hide();
                        $(".alert-success").hide();
                        $(".zip-archive").hide();

                        if (data.autoUpload || (data.autoUpload !== false && $(this).fileupload('option', 'autoUpload'))) {
                            data.process().done(function () {
                                data.submit();
                            });
                        }
                    }
                },
                error: function (e, data) {

                },
                success: function (resp) {
                    $(".userID").val(resp.uuid);
                    $(".dropify-wrapper").addClass("has-preview");
                    $(".dropify-preview").css("display","block");
                    $(".alert-info").show();
                }
            });
        });
    });
</script>
</body>
</html>
