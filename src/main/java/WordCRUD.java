import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WordCRUD implements ICRUD{
    ArrayList<Word> list;
    Scanner s;

    WordCRUD(Scanner s){
        list = new ArrayList<>();
        this.s = s;
    }

    @Override
    public Object add() {
        System.out.println("=> 난이도(1,2,3) & 새 단어 입력 : ");
        int level = s.nextInt();
        String word = s.nextLine();
        System.out.println("뜻 입력 : ");
        String meaning = s.nextLine();

        return new Word(0, level, word, meaning);
    }

    public void addItem(){
        Word one = (Word)add();
        list.add(one);
        System.out.println("새 단어가 단어장에 추가되었습니다. ");
    }

    @Override
    public int update(Object obj) {
        return 0;
    }

    @Override
    public int delete(Object obj) {
        return 0;
    }

    @Override
    public void selectOne(int id) {

    }

    public void listAll(){
        System.out.println("-----------------------------------");
        for(int i=0; i< list.size(); i++){
            System.out.print((i+1) + " ");
            System.out.println(list.get(i).toString());
        }
        System.out.println("-----------------------------------");
    }

    public ArrayList<Integer> listAll(String keyword){
        ArrayList<Integer> idlist = new ArrayList<Integer>();
        int j =0;
        System.out.println("-----------------------------------");
        for(int i=0; i< list.size(); i++){
            String word = list.get(i).getWord();
            if(!word.contains(keyword)) continue;
            System.out.print((j+1) + " ");
            System.out.println(list.get(i).toString());
            idlist.add(i);
            j++;
        }
        System.out.println("-----------------------------------");
        return idlist;
    }

    public void listAll(int level){
        ArrayList<Integer> idlist = new ArrayList<Integer>();
        int j =0;
        System.out.println("-----------------------------------");
        for(int i=0; i< list.size(); i++){
            int l = list.get(i).getLevel();
            if(l != level) continue;
            System.out.print((j+1) + " ");
            System.out.println(list.get(i).toString());
            j++;
        }
        System.out.println("-----------------------------------");
    }

    public void updateItem() {
        System.out.print("=> 수정할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.print("=> 수정할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        System.out.print("=> 뜻 입력 : ");
        String meaning = s.nextLine();
        Word word = list.get(idlist.get(id-1));
        word.setMeaning(meaning);
        System.out.println("단어가 수정되었습니다.  ");
    }

    public void deleteItem() {
        System.out.print("=> 삭제할 단어 검색 : ");
        String keyword = s.next();
        ArrayList<Integer> idlist = this.listAll(keyword);
        System.out.print("=> 삭제할 번호 선택 : ");
        int id = s.nextInt();
        s.nextLine();
        System.out.print("=> 정말로 삭제하시겠습니까?(Y/N) ");
        String answer = s.next();
        if(answer.equalsIgnoreCase("y")){
            list.remove((int)idlist.get(id-1));
            System.out.println("단어 삭제 완료 ");
        }
        else System.out.println("단어 삭제 취소 ");
    }

    public void loadFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MyDictoinary.txt"));
            String line;
            int count = 0;
            while(true){
                line = reader.readLine();
                if(line == null) break;

                String data[] = line.split("\\|");
                int level = Integer.parseInt(data[0]);
                String word = data[1];
                String meaning = data[2];
                list.add(new Word(0, level, word, meaning));
                count++;
            }

            reader.close();
            System.out.println("=> " + count + "개 단어 로딩완료 ");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveFile() {
        try {
            PrintWriter print = new PrintWriter(new FileWriter("MyDictionary.txt"));
            for(Word word : list){
                print.write(word.toFileSting() + "\n");
                //write는 개행을 하지 않기 때문에 개행 \n 추가.
            }
            print.close();
            System.out.println("=> 파일 저장 완료 ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void searchLevel() {
        System.out.print("=> 원하는 레벨은? (1~3): ");
        int level = s.nextInt();
        listAll(level);
    }

    public void searchWord() {
        System.out.print("=> 원하는 단어는? ");
        String word = s.next();
        listAll(word);
    }
}
