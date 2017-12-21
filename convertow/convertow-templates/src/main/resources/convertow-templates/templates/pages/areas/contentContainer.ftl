[#-------------- ASSIGNMENTS --------------]
[#assign cssClass=def.parameters.cssClass!""]

[#-------------- RENDERING --------------]
<div class="${cssClass}">
[#list components as component ]
    [@cms.component content=component /]
[/#list]
</div>
