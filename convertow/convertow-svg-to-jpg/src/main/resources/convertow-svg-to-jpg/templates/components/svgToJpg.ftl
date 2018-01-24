[#assign restPath = ctx.contextPath + "/.rest/svgtojpg/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "svg";
    var restPath = "${restPath!}";
</script>
