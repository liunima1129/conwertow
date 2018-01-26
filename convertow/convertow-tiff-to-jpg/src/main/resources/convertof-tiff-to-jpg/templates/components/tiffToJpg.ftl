[#assign restPath = ctx.contextPath + "/.rest/tifftojpg/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "tiff";
    var restPath = "${restPath!}";
</script>

