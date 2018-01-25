[#assign restPath = ctx.contextPath + "/.rest/svgtotiff/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "svg";
    var restPath = "${restPath!}";
</script>
