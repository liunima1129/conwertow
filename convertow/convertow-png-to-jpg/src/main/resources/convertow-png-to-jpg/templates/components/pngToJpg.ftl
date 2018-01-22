[#assign restPath = ctx.contextPath + "/.rest/pngtojpg/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "png";
    var restPath = "${restPath!}";
</script>