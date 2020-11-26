
package cribaerastotenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class CribaErastotenes {

    int numero;
    ArrayList <Integer> taboa = null;
    File arquivo = new File ("primos.txt");

    public CribaErastotenes (){
    }
    
    public CribaErastotenes (int numero){
        this.numero = numero;
    }
 
   
    /*Este método comproba que si o número introducido é primo ou non.
    No caso de selo, o retorna tal cual o seu valor. 
    Si non é primo (desbotando os múltiplos de 1, e os que divididos polos números
    xa almacenados no array), devolve 0, xa que o método que almacena os primos na
    táboa comproba que o valor do número no sexa 0 ou que esté incluído xa na táboa.
    */
    public Integer isPrimo(int numero){
        readTable();
        for (int i = 0; i < taboa.size(); i++){
            int leido = taboa.get(i);
            if (leido == numero){
                break;
            }else{
                if ((leido != 1) && (numero % leido == 0)){
                    numero = 0;
                    break;
                }
            }
        }
        return numero;
    }
    
    /*Este método le o arquivo serializado donde está a táboa de primos, e a 
    almacena nun array para que a app a recorra. No caso de que a táboa esté vacía,
    introduzo os 3 primeiros números primos xa que é máis sinxelo de facelo así.
    */
    public void readTable (){
        try 
            (ObjectInputStream ois = new ObjectInputStream (new FileInputStream (arquivo))){
            taboa = (ArrayList <Integer>)ois.readObject();
        } catch (FileNotFoundException fnfe) {
            taboa = new ArrayList <>();
        }catch (ClassNotFoundException cnfe){
            System.out.println(cnfe.getMessage());
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());   
        }
        if (taboa.isEmpty()){
            taboa.add(1);
        }
    }

    
    /* No caso de que o método que calcula si o número é primo non devolve un 0,
    e o array non contén xa ese número, o almacena.
    Co método sort da clase Collections, ordeno de menor a maior os números 
    almacenados para saber ben a posición.
    */ 
    public void addPrimo(int numero){
        if (numero != 0){
        try 
            (ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (arquivo))){
            if (!taboa.contains(numero))
            taboa.add(numero);
            oos.writeObject(taboa);
            Collections.sort(taboa);
            oos.flush();
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
        }
    }
    
    /*Aquí creo a táboa de Erastótenes, indicando como parámetro o número maior
    que quero calcular, hasta qué número. 
    */
    public Integer createTable(int numero){
        int contador = 1;
        while (numero >= contador){
            addPrimo(isPrimo(contador));
            contador ++;
        }
        System.out.println(taboa);
        return numero;
    }
    
    /*Para calcular qué valor ocupa unha determidada posición, introduzo como
    parámetro dita posición e co método get() de ArrayList me devolve o valor de 
    dita posición. Tendo en conta que o array comeza a contar co 0, debo restar 
    un valor ó número solicitado para que empece a contar no 1, non no 0.
    */
    public Integer posicion (int numero){
        int posicion = numero --;
        int sizeArray = taboa.size();
        if (posicion <= sizeArray){
        int valor = 0;
        valor = taboa.get(numero);
        System.out.println("El primo número " + posicion + " es el " + valor + ".");
        }
        return numero;
    }
    
    
    public static void main(String[] args) {
        Scanner teclado = new Scanner (System.in);
        CribaErastotenes ce = new CribaErastotenes ();
        ce.addPrimo(ce.isPrimo(ce.createTable(1000)));
        //ce.saveTable();
        ce.readTable();
        System.out.println("Indica la posición de la que desea conocer el valor ");
        int numero = teclado.nextInt();
        ce.posicion(numero);
        //System.out.println(ce.taboa.size());
    }
    
}
