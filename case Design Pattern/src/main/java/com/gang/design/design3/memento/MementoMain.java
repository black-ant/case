package com.gang.design.design3.memento;

/**
 * @Classname MementoMain
 * @Description TODO
 * @Date 2020/12/13 14:41
 * @Created by zengzg
 */
public class MementoMain {
    public static void main(String[] args) {

        // ����ԭʼ��
        Original origi = new Original("egg");

        // ��������¼
        Storage storage = new Storage(origi.createMemento());

        // �޸�ԭʼ���״̬
        System.out.println("��ʼ��״̬Ϊ��" + origi.getValue());
        origi.setValue("niu");
        System.out.println("�޸ĺ��״̬Ϊ��" + origi.getValue());

        // �ظ�ԭʼ���״̬
        origi.restoreMemento(storage.getMemento());
        System.out.println("�ָ����״̬Ϊ��" + origi.getValue());
    }
}
