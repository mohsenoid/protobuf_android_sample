package com.mirhoseini.protobuftest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bytes = createAddressBook()

        println(AddressBookProtos.AddressBook.parseFrom(bytes))
    }

    private fun createAddressBook(): ByteArray? {
        val phoneHome = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("123456")
                .setType(AddressBookProtos.Person.PhoneType.HOME)
                .build()

        val phoneMobile = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("654321")
                .setType(AddressBookProtos.Person.PhoneType.MOBILE)
                .build()

        val person = AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("Mohsen")
                .setEmail("mohsen@mirhoseini.info")
                .addAllPhones(listOf(phoneHome, phoneMobile))
                .build()

        val addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addAllPeople(listOf(person))
                .build()

        return addressBook.toByteArray()
    }
}
