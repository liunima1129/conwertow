[#assign linkHref = item[field + 'external']!]
[#assign linkText = item['title' + field]!item['title' + field?cap_first]!item[field + 'external']!]
[#assign linkTarget = " target=\"_blank\""]
