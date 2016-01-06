import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Михаил on 03.01.2016.
 *
 */
public class Test2 {
    public Double calc3(String e){
        StringTokenizer st = new StringTokenizer(e,"\\d +-()*/^",true);
        List<String> list = new LinkedList<>();
        //определить максимальный и минимальный приоритет операций
        int minP=100;
        int maxP=-1;
        boolean firstLetter = true;
        //и заодно сделать список
        while (st.hasMoreTokens()) {
            String t = st.nextToken();
            list.add(t);
            int pri;
            switch (t){
                case "(": pri =1;break;
                case ")": pri =1;break;
                case "+": pri =2;break;
                case "-": pri =2;break;
                case "*": pri =3;break;
                case "/": pri =3;break;
                case "^": pri =4;break;
                default:continue;
            }
            if(minP>pri && !firstLetter) minP=pri;
            if(maxP<pri && !firstLetter) maxP=pri;
            firstLetter=false;
        }
        if(minP==1) return Double.NaN;//пока без скобок

        if(minP == maxP && list.size()>=3) { //выполнить операции с равным приоритетом
            Double v1,v2;
            int ip = 1;
            try{v1 = Double.parseDouble(list.get(0));} catch (NumberFormatException exc){
                ip++;
                switch (list.get(0)){
                    case "-":v1 = -Double.parseDouble(list.get(1));break;
                    case "+":v1 = Double.parseDouble(list.get(1));break;
                    default: return Double.NaN;
                }
            }
            while(ip<list.size()) {
                v2 = Double.parseDouble(list.get(ip+1));
                switch (list.get(ip)) {
                    case "+": v1 = v1 + v2;break;
                    case "-": v1 = v1 - v2;break;
                    case "*": v1 = v1 * v2;break;
                    case "/": v1 = v1 / v2;break;
                    case "^": v1 = Math.pow(v1, v2);break;
                    default: return Double.NaN;//пока не знаю как вычислить
                }
                ip+=2;
            }
            return v1;

        } else if(list.size()==2) { //знак числа, видимо
            if(list.get(0).equals("+")) return Double.parseDouble(list.get(1));
            if(list.get(0).equals("-")) return -Double.parseDouble(list.get(1));
        } else if(list.size()==1) return Double.parseDouble(list.get(0));//просто число

        return Double.NaN;//пока не знаю как вычислять
    }

    public Double calc(String st){
        return Double.NaN;
    }

    public static void main(String[] args) {
        String str = "26*69+(-3)+23-(76+85)^(2+(9-7))";

        Test2 t = new Test2();
        //Double v = t.calc("26*69")+t.calc("-3")+t.calc("23")-Math.pow(t.calc("76+85"),t.calc("2")+t.calc("9-7"));

        String st = "-2*2*3*6";
        Double v = t.calc(st);

        System.out.println(st+" = "+v);

    }
}
