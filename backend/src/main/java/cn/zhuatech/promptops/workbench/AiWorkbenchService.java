/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.promptops.workbench;
import org.springframework.stereotype.Service;
import java.math.*;
import java.util.*;
import static cn.zhuatech.promptops.workbench.WorkbenchInput.*;
/** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
@Service
public class AiWorkbenchService {
    /** 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。 */
    public Map<String,Object> execute(Map<String,Object> r){
        validate(r);

        keys(r,"template","variables");String template=text(r,"template",32000);var variables=object(r.get("variables"));if(variables.size()>100)throw new IllegalArgumentException("变量最多100项");
        for(var e:variables.entrySet())if(!e.getKey().matches("[A-Za-z_][A-Za-z0-9_]{0,63}")||!(e.getValue() instanceof String s)||s.length()>8000)throw new IllegalArgumentException("变量名称或文本值无效");
        java.util.regex.Pattern p=java.util.regex.Pattern.compile("\\{\\{\\s*([A-Za-z_][A-Za-z0-9_]{0,63})\\s*}}");var matcher=p.matcher(template);Set<String> required=new TreeSet<>();StringBuilder out=new StringBuilder();int cursor=0,count=0;
        while(matcher.find()){String name=matcher.group(1);required.add(name);out.append(template,cursor,matcher.start()).append(variables.getOrDefault(name,matcher.group()));cursor=matcher.end();count++;if(out.length()>64000)throw new IllegalArgumentException("渲染结果过长");}out.append(template.substring(cursor));if(out.length()>64000)throw new IllegalArgumentException("渲染结果过长");
        String stripped=p.matcher(template).replaceAll("");boolean malformed=stripped.contains("{{")||stripped.contains("}}");List<String> missing=required.stream().filter(x->!variables.containsKey(x)).toList();List<String> unused=variables.keySet().stream().filter(x->!required.contains(x)).sorted().toList();
        boolean valid=missing.isEmpty()&&!malformed;return Map.of("valid",valid,"requiredVariables",List.copyOf(required),"missingVariables",missing,"unusedVariables",unused,"occurrences",count,"malformedBraces",malformed,"rendered",valid?out.toString():"");

    }
}
