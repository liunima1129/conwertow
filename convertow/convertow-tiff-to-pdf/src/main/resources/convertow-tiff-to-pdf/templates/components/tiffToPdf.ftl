[#assign restPath = ctx.contextPath + "/.rest/tifftopdf/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "tiff";
    var restPath = "${restPath!}";
</script>

