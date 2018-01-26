[#assign restPath = ctx.contextPath + "/.rest/tifftopng/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "tiff";
    var restPath = "${restPath!}";
</script>

