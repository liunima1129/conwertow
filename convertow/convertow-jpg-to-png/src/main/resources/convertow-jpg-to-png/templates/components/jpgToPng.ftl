[#assign restPath = ctx.contextPath + "/.rest/jpgtopng/convert?id="/]

[#include "/convertow-templates/templates/components/content/dropzone.ftl"]

<script>
    var fileSupported = "jpg";
    var restPath = "${restPath!}";
</script>