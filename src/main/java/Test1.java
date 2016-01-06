import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Михаил on 02.01.2016.
 */
public class Test1 {

    public static Stack<String> GenerateValues(String string){
        Pattern p = Pattern.compile("^[\\d.]+");
        Stack<String> valStack = new Stack<>();
        Stack<String> opStack = new Stack<>();
        Map<String,Integer> priority = new HashMap<>();
        priority.put("(",0);
        priority.put(")",0);
        priority.put("+",10);
        priority.put("-",10);
        priority.put("*",20);
        priority.put("/",20);
        priority.put("^",30);

        String str = string.replaceAll("\\s","");
        StringTokenizer st = new StringTokenizer(str,"\\d +-()*/^",true);
        while (st.hasMoreTokens()){
            String el = st.nextToken();
            Matcher m = p.matcher(el);
            if(m.matches()) valStack.push(el);//если элементом является число, то запихать число
            else{
                if(opStack.isEmpty()) opStack.push(el);//если в стеке операций ничего нет, то запихать туда
                else if("(".equals(el)) opStack.push(el);//если это открытая скобка, от отправить ее в стек
                else if(")".equals(el)){//если закрытая скобка раскрутить стек до открывающийся скобки
                    String e;
                    do{
                        e = opStack.pop();
                        if("(".equals(e)) break;
                        valStack.push(e);
                    } while (!opStack.isEmpty());
                } else { //иначе это просто операция
                    Integer pc,po;
                    pc = priority.get(el);
                    po = priority.get(opStack.peek());
                    if(pc!=null && po!=null){//если операция известна (ошибка)
                        if(pc>po) opStack.push(el);//если приоритет текущей операции более прошлой то запихать команду в стек
                        else{
                            do{
                                String e = opStack.pop();
                                po = priority.get(e);
                                if(po == null) break;// (ошибка)
                                if(!"(".equals(e)) valStack.push(e);
                                if(pc>po || opStack.isEmpty()) {opStack.push(el);break;}//часть алгоритма
                            } while(!opStack.isEmpty());
                        }
                    }
                }
            }
        }
        while(!opStack.isEmpty()) valStack.push(opStack.pop());//перенести оставшиеся команды
        return valStack;
    }

    public static Double CalcValue(Stack<String> stack){
        List<String> list = new ArrayList<String>(stack);
        Stack<Double> res = new Stack<>();
        for (String s : list) {
            try {
                Double v = Double.parseDouble(s);
                res.push(v);
            } catch (NumberFormatException e){
                Double v;
                Double v1 = res.pop();
                Double v2 = res.pop();
                if(v1==null || v2==null) return null; //ошибка в выражении
                switch (s){
                    case "+":
                        v=v2+v1;
                        break;
                    case "-":
                        v=v2-v1;
                        break;
                    case "*":
                        v=v2*v1;
                        break;
                    case "/":
                        v=v2/v1;
                        break;
                    case "^":
                        v=Math.pow(v2,v1);
                        break;
                    default:
                        return null;
                }
                res.push(v);
            }
        }

        if(res.size()!=1) return null;
        return res.pop();
    }


    public static void main(String[] args) {
        String st = "2+(7*1)+5^(1+1)";
        Stack<String> val = GenerateValues(st);

        Double v = CalcValue(val);

        System.out.println(st + " = " + v);
    }
}
