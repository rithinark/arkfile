class Node:
    def __init__(self, data):
        self.data = data
        self.next = None


class LinkedList:
    def __init__(self):
        self.__head = None


    def add(self, element, INDEX=None):
        if INDEX is None:  # inserts at beginning
            if self.__head is None:
                self.__head = Node(element)
            else:
                new_node = Node(element)
                new_node.next = self.__head
                self.__head = new_node
        # --------------by index-----------------
        else:  # insert element by index
            index = 0
            node = self.__head
            prenode = None

            while node:
                if index == INDEX:
                    new_node = Node(element)
                    prenode.next = new_node
                    new_node.next = node
                    return
                prenode = node
                index += 1
                node = node.next
            self.add(element)

    def delete(self, element=None):
        if element is None:  # deletes at beginning
            if self.__head is None:
                print("empty list")
            else:
                self.__head = self.__head.next

        # -------------by value-----------------

        else:  # delete by element
            node = self.__head
            preNode = None
            while node:
                if node.data == element:
                    preNode.next = node.next
                    return
                preNode = node
                node = node.next
            print("element Not Found")

    def index(self, element):  # searches by element
        node = self.__head
        index = 0
        while node:
            if node.data == element:
                return index
            index += 1
            node = node.next
        return False

    def get(self, INDEX):
        if self.__head is None:
            return None
        else:
            index = 0
            node = self.__head
            while node:
                if index == INDEX:
                    return node.data
                index += 1
                node = node.next
            return False

    def len(self):
        if self.__head is None:
            return 0
        data = self.__head
        index = 0
        while data:
            index += 1
            data = data.next
        return index

    def __str__(self):
        data = self.__head
        string="{"
        while data:
            string+=str(data.data)
            if data.next is not None:
                string+=','
            data = data.next
        string+="}"
        return string


    def __call__(self,INDEX):
        return self.get(INDEX)


    def __iter__(self):
        return self

    iterator=-1
    def __next__(self):
        if self.__head ==None:
            return None
        self.iterator+=1
        if self.iterator<self.len():

            return self.get(self.iterator)
        raise StopIteration




