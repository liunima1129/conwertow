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
<script>
    var contextPath = "${ctx.contextPath!}";
    var fileName;
    $(document).ready(function(){

        $(function() {
            $('.lazy').lazy();
        });

        $(".dropify-convert").click(function (e) {
            e.preventDefault();

            var canGo = true;
            var url = restPath + $(".userID").val() +"&name=" + $(".fileName").val();

            var password = $("#password").val();
            var retypepassword = $("#retypepassword").val();

            /*url = restPath + $(".userID").val() +"&name=" + $(".fileName").val() +"&password=" + password;*/

            if( $("#protectPDF").length > 0 ){
                console.log( "Validation start" );

                if( password != retypepassword ){
                    canGo = false;
                    console.log("Validation error");
                    $(".alert-danger").hide();
                    $(".alert-success").hide();
                    $(".zip-archive").hide();
                    $(".alert-info").hide();
                    $(".pdf-protect-error").show();
                }else{
                    console.log("Validation success");
                    $(".pdf-protect-error").hide();
                    $(".alert-danger").hide();
                    $(".alert-success").hide();
                    $(".zip-archive").hide();
                }
                url = restPath + $(".userID").val() +"&name=" + $(".fileName").val() +"&password=" + password;
            }

            if ( canGo ){
                $(".alert-danger").hide();
                $(".alert-success").hide();
                $(".zip-archive").hide();

                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: url,
                    success: function(e, data){
                        if ( singleFile && !zipFile){
                            $(".info-zip-link").hide();
                            var name = $("#fileName").val();
                            var userId = $("#userID").val();
                            var output = name.substr(0, name.lastIndexOf('.')) || name;
                            var res = "/docroot/" + userId + "/" + output + resultExtension;
                            $(".file-link").attr("href", res);
                        }
                        if( !singleFile && zipFile ){
                            $(".info-zip-link").show();
                            var name = $("#fileName").val();
                            var userId = $("#userID").val();
                            var output = name.substr(0, name.lastIndexOf('.')) || name;
                            var res = "/docroot/" + userId + "/" + output + resultExtension;
                            $(".file-link").attr("href", res);
                            $(".info-zip-link").attr("href", res);
                        }
                        $(".alert-success").show();
                        $(".zip-archive").show();
                        $(".alert-info").hide();
                        $(".protect-form").hide();

                        var error = e.error;

                        if ( error === 1 ){
                            console.log("Handle strong protected file!");
                            //hide success info and download button
                            //unprotect tool
                            $(".alert-success").hide();
                            $(".zip-archive").hide();
                            $(".error-unlock-pdf").show();
                            $(".protect-form").show();
                        }
                    },
                    error: function (e) {
                        $(".alert-info").hide();
                        $(".alert-danger").hide();
                        $(".zip-archive").hide();
                    }
                });
            }
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
                    console.log("Done file upload");
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
                    console.log("Error file upload");
                },
                success: function (resp) {
                    console.log("Success file upload");
                    $(".userID").val(resp.uuid);
                    $(".dropify-wrapper").addClass("has-preview");
                    $(".dropify-preview").css("display","block");
                    $(".alert-info").show();
                    $(".error-unlock-pdf").hide();

                    if( $("#protectPDF").length > 0 ) {
                        $(".pdf-protect-error").hide();
                        $(".protect-form").show();
                    }
                }
            });
        });
    });
</script>
</body>
</html>
