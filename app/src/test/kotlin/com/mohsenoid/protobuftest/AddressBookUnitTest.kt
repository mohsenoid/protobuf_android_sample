package com.mohsenoid.protobuftest

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddressBookUnitTest {

    private lateinit var addressBook: AddressBookProtos.AddressBook
    private lateinit var addressBookBytes: ByteArray
    private lateinit var sampleJsonBytes: ByteArray

    @Before
    fun prepare() {
        addressBook = createAddressBookProtobuf()
        addressBookBytes = addressBook.toByteArray()
        sampleJsonBytes = SAMPLE_JSON.toByteArray()
    }

    private fun createAddressBookProtobuf(): AddressBookProtos.AddressBook {
        // building PhoneNumber objects
        val phoneHome = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49123456")
                .setType(AddressBookProtos.Person.PhoneType.HOME)
                .build()
        val phoneMobile = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49654321")
                .setType(AddressBookProtos.Person.PhoneType.MOBILE)
                .build()

        // building a Person object using phone data
        val person = AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("Mohsen")
                .setEmail("info@mohsenoid.com")
                .addAllPhones(listOf(phoneHome, phoneMobile))
                .build()

        // building an AddressBook object using person data
        return AddressBookProtos.AddressBook.newBuilder()
                .addAllPeople(listOf(person))
                .build()
    }

    @Test
    fun serialization_isCorrect() {
        val myAddressBook = AddressBookProtos.AddressBook.parseFrom(addressBookBytes)
        println(myAddressBook)

        assertEquals(addressBook, myAddressBook)
    }

    @Test
    fun protobuf_vs_json() {
        println("addressBook: ${addressBookBytes.size} byte(s) << sampleJson: ${sampleJsonBytes.size} byte(s)")
        println()
        println("""Min BLE MTU (maximum transmission unit): 20 bytes ⇒
                                addressBook ≃ ${addressBookBytes.size / 20} package(s)
                                sampleJson ≃ ${sampleJsonBytes.size / 20} package(s)""")

        assertTrue(addressBookBytes.size < (sampleJsonBytes.size / 6))
    }

    @After
    fun printSeparator(){
        println("**************************************************************")
    }

    companion object {
        private const val SAMPLE_JSON =
                """{
  "addressbook": [
    {
      "person": {
        "id": 1,
        "name": "Mohsen",
        "email": "info@mohsenoid.com"
      }
    },
    {
      "phones": [
        {
          "phone": {
            "number": "+49123456",
            "type": "HOME"
          }
        },
        {
          "phone": {
            "number": "+49654321",
            "type": "MOBILE"
          }
        }
      ]
    }
  ]
}"""
    }
}
