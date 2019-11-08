package Lesson3;

import java.util.HashMap;

public class HomeWork {
    //    1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и вывести список уникальных
//    слов, из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.
//            2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров и
//    электронных почт. В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get()
//    искать номер телефона по фамилии. Следует учесть, что под одной фамилией может быть несколько телефонов
//            (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.
    public static void main(String[] args) {
        String str1 = "Отчет по реализации и поступлению товаров и услуг для 1С:Бухгалтерии 3.0 rar 417360 УПД за поставщика для" +
                " документа Поступление товаров и услуг для БП 3.0 (ver.1.5.4) 337286 Закрытие счетов 25 и 26 на счета" +
                " 20.01 и 08.03 в Бухгалтерии 3.0 rar 536900 Договор-счет на услуги epf 942285 Начисление процентов по кредитам " +
                "и займам для 1С:Бухгалтерии 3.0 rar 447389 Акт передачи прав для 1С: Бухгалтерия 8, редакции 3 rar 179777 " +
                "Групповой ввод Требований-накладных для БП 3.0 rar 696300 Выгрузка физических лиц из ЗУП 3.1.3 в БП 3.0 zip 683138" +
                " Акт сверки расчетов с контрагентом для БП 3.0 rar 330270 Анализ расхождения доходов от реализации" +
                " из декларации НДС и декларации по налогу на прибыль erf 504958 Печатные формы для БП 3.0 epf 518516 Перепродажи" +
                " (интеркампани) между собственными фирмами для БП 3.0 rar 331583 Заявление на выдачу денежных средств под отчет" +
                " rar 312953 Легкие правила переноса остатков из БП 3.0 в БП 3.0" +
                " документами  rar 964076 Инвентаризация расходов будущих периодов ИНВ-11 для БП 3.0 rar 320815 Интерактивная" +
                " оборотно-сальдовая ведомость 3.0 для управляемых форм 3.0.13.83 3.0.18.2 377955 Карточка количественно-стоимостного" +
                " учета номенклатуры для Бухгалтерии 3.0 erf 804936 Перенос проводок с одного счета на другой без проведения" +
                " документов для Бухгалтерии 3.0 с сохранением значений субконто. epf 807075 Заполнение статистической" +
                " формы учета перемещения товаров при экспорте в страны ТС для Бухгалтерии 3.0 rar 914242 Коммерческое предложение" +
                " для счета на оплату БП 3.0 rar rar 455660 Проведение снятие с проведения документов очистки движений не проведенных" +
                " перезаписи ссылочных объектов";


        String strArr[] = (str1.split("\\s+"));
        HashMap<String, Integer> wordToCount = new HashMap<>();
        for (String word : strArr)
        {
            if (!wordToCount.containsKey(word))
            {
                wordToCount.put(word, 0);
            }
            wordToCount.put(word, wordToCount.get(word) + 1);
        }
        for (String word : wordToCount.keySet())
        {
            System.out.println("Слово "+word + " встречается " + wordToCount.get(word)+" раз(а)");
        }
    }
}