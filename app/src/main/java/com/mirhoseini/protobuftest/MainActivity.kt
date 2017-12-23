package com.mirhoseini.protobuftest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bytes = testAddressBookProtobuf()

        // You can deserialize AddressBook bytes
        val myAddressBook = AddressBookProtos.AddressBook.parseFrom(bytes)
        println(myAddressBook)

        println("Protobuf byte size: ${bytes.size}")
        println("JSON byte size: ${sampleJson.toByteArray().size}")
    }

    private fun testAddressBookProtobuf(): ByteArray {
        // building PhoneNumber objects
        val phoneHome = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("123456")
                .setType(AddressBookProtos.Person.PhoneType.HOME)
                .build()
        val phoneMobile = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("654321")
                .setType(AddressBookProtos.Person.PhoneType.MOBILE)
                .build()

        // building a Person object using phone data
        val person = AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("Mohsen")
                .setEmail("mohsen@mirhoseini.info")
                .addAllPhones(listOf(phoneHome, phoneMobile))
                .build()

        // building an AddressBook object using person data
        val addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addAllPeople(listOf(person))
                .build()

        // finally this is how you get serialized ByteArray
        return addressBook.toByteArray()
    }

    private val sampleJson = """{
  "addressbook": [
    {
      "person": {
        "id": 1,
        "name": "Mohsen",
        "email": "mohsen@mirhoseini.info"
      }
    },
    {
      "phones": [
        {
          "phone": {
            "number": "123456",
            "type": "HOME"
          }
        },
        {
          "phone": {
            "number": "654321",
            "type": "MOBILE"
          }
        }
      ]
    }
  ]
}"""
}

