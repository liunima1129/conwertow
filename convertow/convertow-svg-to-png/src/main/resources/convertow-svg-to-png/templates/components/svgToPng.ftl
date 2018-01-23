[#assign restPath = ctx.contextPath + "/.rest/svgtopng/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "svg";
    var restPath = "${restPath!}";
</script>
