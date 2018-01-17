[#assign restPath = ctx.contextPath + "/.rest/pdftojpg/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "pdf";
    var restPath = "${restPath!}";
</script>