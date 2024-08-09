package com.incirkus.connect.DATA.local

import com.incirkus.connect.DATA.Model.ChatRoom
import com.incirkus.connect.DATA.Model.Department
import com.incirkus.connect.DATA.Model.User
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.R


//old data-structure
/*
object SampleData {

    val sampleContacts = listOf(
        Contact(id = 1, name = "Alice Smith",       image = R.drawable.brad,        email = "alice@example.com", phoneNumber = "1234567890", password = "password123"),
        Contact(id = 2, name = "Bob Johnson",       image = R.drawable.emma,        email = "bob@example.com", phoneNumber = "1234567891", password = "password123"),
        Contact(id = 3, name = "Charlie Brown",     image = R.drawable.jennifer,    email = "charlie@example.com", phoneNumber = "1234567892", password = "password123"),
        Contact(id = 4, name = "David Wilson",      image = R.drawable.johnny,      email = "david@example.com", phoneNumber = "1234567893", password = "password123"),
        Contact(id = 5, name = "Eve Davis",         image = R.drawable.keanu,       email = "eve@example.com", phoneNumber = "1234567894", password = "password123"),
        Contact(id = 6, name = "Frank Clark",       image = R.drawable.leo,         email = "frank@example.com", phoneNumber = "1234567895", password = "password123"),
        Contact(id = 7, name = "Grace Lewis",       image = R.drawable.margot,      email = "grace@example.com", phoneNumber = "1234567896", password = "password123"),
        Contact(id = 8, name = "Hank Lee",          image = R.drawable.reese,       email = "hank@example.com", phoneNumber = "1234567897", password = "password123"),
        Contact(id = 9, name = "Ivy Walker",        image = R.drawable.sandra,      email = "ivy@example.com", phoneNumber = "1234567898", password = "password123"),
        Contact(id = 10, name = "Jack Hall",        image = R.drawable.scarlett,    email = "jack@example.com", phoneNumber = "1234567899", password = "password123"),
        Contact(id = 11, name = "Karen Young",      image = R.drawable.tom,         email = "karen@example.com", phoneNumber = "1234567800", password = "password123"),
        Contact(id = 12, name = "Leo King",         image = R.drawable.will,        email = "leo@example.com", phoneNumber = "1234567801", password = "password123"),
        Contact(id = 13, name = "Mia Wright",       image = R.drawable.brad,        email = "mia@example.com", phoneNumber = "1234567802", password = "password123"),
        Contact(id = 14, name = "Nina Scott",       image = R.drawable.emma,        email = "nina@example.com", phoneNumber = "1234567803", password = "password123"),
        Contact(id = 15, name = "Oscar Green",      image = R.drawable.jennifer,    email = "oscar@example.com", phoneNumber = "1234567804", password = "password123"),
        Contact(id = 16, name = "Paul Adams",       image = R.drawable.johnny,      email = "paul@example.com", phoneNumber = "1234567805", password = "password123"),
        Contact(id = 17, name = "Quinn Baker",      image = R.drawable.keanu,       email = "quinn@example.com", phoneNumber = "1234567806", password = "password123"),
        Contact(id = 18, name = "Ruby Gonzalez",    image = R.drawable.leo,        email = "ruby@example.com", phoneNumber = "1234567807", password = "password123"),
        Contact(id = 19, name = "Sam Hill",         image = R.drawable.margot,      email = "sam@example.com", phoneNumber = "1234567808", password = "password123"),
        Contact(id = 20, name = "Tina Martinez",    image = R.drawable.reese,      email = "tina@example.com", phoneNumber = "1234567809", password = "password123")
    )

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val currentDate = dateFormat.format(Date())

    val sampleMessages = listOf(
        // Nachrichten zwischen Alice Smith und Bob Johnson
        Message(id = 1, senderID = 1, recipientID = 2, message = "Hello Bob, how are you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 2, senderID = 2, recipientID = 1, message = "Hi Alice, I'm good. How about you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 3, senderID = 1, recipientID = 2, message = "I'm great, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 4, senderID = 2, recipientID = 1, message = "What are you up to?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 5, senderID = 1, recipientID = 2, message = "Just working on a project.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 6, senderID = 2, recipientID = 1, message = "Sounds interesting.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 7, senderID = 1, recipientID = 2, message = "Yes, it is. How about you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 8, senderID = 2, recipientID = 1, message = "I'm just relaxing today.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 9, senderID = 1, recipientID = 2, message = "Nice! Enjoy your day.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 10, senderID = 2, recipientID = 1, message = "Thank you! You too.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Charlie Brown und David Wilson
        Message(id = 11, senderID = 3, recipientID = 4, message = "Hey David, long time no see!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 12, senderID = 4, recipientID = 3, message = "Hey Charlie! How have you been?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 13, senderID = 3, recipientID = 4, message = "I've been good. Busy with work.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 14, senderID = 4, recipientID = 3, message = "Same here. We should catch up sometime.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 15, senderID = 3, recipientID = 4, message = "Absolutely! Let's plan something.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 16, senderID = 4, recipientID = 3, message = "How about this weekend?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 17, senderID = 3, recipientID = 4, message = "Sounds good to me.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 18, senderID = 4, recipientID = 3, message = "Great! I'll call you later to finalize.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 19, senderID = 3, recipientID = 4, message = "Okay, talk to you soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 20, senderID = 4, recipientID = 3, message = "Bye!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Eve Davis und Frank Clark
        Message(id = 21, senderID = 5, recipientID = 6, message = "Hi Frank, how's it going?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 22, senderID = 6, recipientID = 5, message = "Hey Eve, all good here. You?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 23, senderID = 5, recipientID = 6, message = "Doing well, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 24, senderID = 6, recipientID = 5, message = "What have you been up to?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 25, senderID = 5, recipientID = 6, message = "Just finished a new book.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 26, senderID = 6, recipientID = 5, message = "Nice! Which one?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 27, senderID = 5, recipientID = 6, message = "A thriller by John Grisham.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 28, senderID = 6, recipientID = 5, message = "Sounds exciting.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 29, senderID = 5, recipientID = 6, message = "It was! Highly recommend.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 30, senderID = 6, recipientID = 5, message = "I'll check it out.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Grace Lewis und Hank Lee
        Message(id = 31, senderID = 7, recipientID = 8, message = "Hello Hank!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 32, senderID = 8, recipientID = 7, message = "Hi Grace!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 33, senderID = 7, recipientID = 8, message = "How's everything?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 34, senderID = 8, recipientID = 7, message = "All good, just busy with work.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 35, senderID = 7, recipientID = 8, message = "I understand. Same here.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 36, senderID = 8, recipientID = 7, message = "Let's catch up soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 37, senderID = 7, recipientID = 8, message = "Sure! When are you free?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 38, senderID = 8, recipientID = 7, message = "How about Friday evening?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 39, senderID = 7, recipientID = 8, message = "Perfect. See you then!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 40, senderID = 8, recipientID = 7, message = "See you!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Weitere Nachrichten zwischen den nächsten Kontakten
        // Nachrichten zwischen Ivy Walker und Jack Hall
        Message(id = 41, senderID = 9, recipientID = 10, message = "Hi Jack, how are things?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 42, senderID = 10, recipientID = 9, message = "Hey Ivy, everything's good. How about you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 43, senderID = 9, recipientID = 10, message = "Doing well, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 44, senderID = 10, recipientID = 9, message = "What's new?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 45, senderID = 9, recipientID = 10, message = "Not much, just the usual.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 46, senderID = 10, recipientID = 9, message = "Same here.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 47, senderID = 9, recipientID = 10, message = "Let's catch up soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 48, senderID = 10, recipientID = 9, message = "Definitely. How about next week?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 49, senderID = 9, recipientID = 10, message = "Sounds good.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 50, senderID = 10, recipientID = 9, message = "See you then.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Karen Young und Leo King
        Message(id = 51, senderID = 11, recipientID = 12, message = "Hey Leo!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 52, senderID = 12, recipientID = 11, message = "Hi Karen!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 53, senderID = 11, recipientID = 12, message = "How are you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 54, senderID = 12, recipientID = 11, message = "I'm good, how about you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 55, senderID = 11, recipientID = 12, message = "Doing well, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 56, senderID = 12, recipientID = 11, message = "What's new?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 57, senderID = 11, recipientID = 12, message = "Not much, just the usual.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 58, senderID = 12, recipientID = 11, message = "Same here.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 59, senderID = 11, recipientID = 12, message = "Let's catch up soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 60, senderID = 12, recipientID = 11, message = "Definitely. How about next week?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Weitere Nachrichten für die restlichen Kontakte
        // ...
        // Nachrichten zwischen Nina Scott und Oscar Green
        Message(id = 61, senderID = 13, recipientID = 14, message = "Hi Oscar, how are you?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 62, senderID = 14, recipientID = 13, message = "Hey Nina, I'm good. You?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 63, senderID = 13, recipientID = 14, message = "Doing well, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 64, senderID = 14, recipientID = 13, message = "What have you been up to?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 65, senderID = 13, recipientID = 14, message = "Just busy with work.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 66, senderID = 14, recipientID = 13, message = "I understand.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 67, senderID = 13, recipientID = 14, message = "Let's catch up soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 68, senderID = 14, recipientID = 13, message = "Sure, how about this weekend?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 69, senderID = 13, recipientID = 14, message = "Works for me.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 70, senderID = 14, recipientID = 13, message = "Great, see you then.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Paul Adams und Quinn Baker
        Message(id = 71, senderID = 15, recipientID = 16, message = "Hi Quinn, how are things?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 72, senderID = 16, recipientID = 15, message = "Hey Paul, everything's good. You?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 73, senderID = 15, recipientID = 16, message = "Doing well, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 74, senderID = 16, recipientID = 15, message = "What's new?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 75, senderID = 15, recipientID = 16, message = "Not much, just the usual.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 76, senderID = 16, recipientID = 15, message = "Same here.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 77, senderID = 15, recipientID = 16, message = "Let's catch up soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 78, senderID = 16, recipientID = 15, message = "Definitely. How about next week?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 79, senderID = 15, recipientID = 16, message = "Sounds good.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 80, senderID = 16, recipientID = 15, message = "See you then.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Ruby Gonzalez und Sam Hill
        Message(id = 81, senderID = 17, recipientID = 18, message = "Hi Sam, how's it going?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 82, senderID = 18, recipientID = 17, message = "Hey Ruby, all good here. You?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 83, senderID = 17, recipientID = 18, message = "Doing well, thanks!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 84, senderID = 18, recipientID = 17, message = "What have you been up to?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 85, senderID = 17, recipientID = 18, message = "Just finished a new book.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 86, senderID = 18, recipientID = 17, message = "Nice! Which one?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 87, senderID = 17, recipientID = 18, message = "A thriller by John Grisham.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 88, senderID = 18, recipientID = 17, message = "Sounds exciting.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 89, senderID = 17, recipientID = 18, message = "It was! Highly recommend.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 90, senderID = 18, recipientID = 17, message = "I'll check it out.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),

        // Nachrichten zwischen Tina Martinez und Alice Smith
        Message(id = 91, senderID = 19, recipientID = 1, message = "Hello Alice!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 92, senderID = 1, recipientID = 19, message = "Hi Tina!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 93, senderID = 19, recipientID = 1, message = "How's everything?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 94, senderID = 1, recipientID = 19, message = "All good, just busy with work.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 95, senderID = 19, recipientID = 1, message = "I understand. Same here.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 96, senderID = 1, recipientID = 19, message = "Let's catch up soon.", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 97, senderID = 19, recipientID = 1, message = "Sure! When are you free?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 98, senderID = 1, recipientID = 19, message = "How about Friday evening?", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 99, senderID = 19, recipientID = 1, message = "Perfect. See you then!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate),
        Message(id = 100, senderID = 1, recipientID = 19, message = "See you!", isRead = false, dispatchDate = currentDate, receiptDate = currentDate)
    )
}*/


//new data-structure

object SampleData {

    // Abteilung: Buchhaltung
    val user1 = User(

        firstName = "Anna",
        lastName = "Müller",
        image = R.drawable.brad,  // Platzhalter für ein Bild
        email = "anna.mueller@logistikfirma.com",
        phoneNumber = "0123456789",
        password = "securepassword1",
        departmentId = "buchhaltung"
    )

    val user2 = User(

        firstName = "Bernd",
        lastName = "Schmidt",
        image = R.drawable.emma,
        email = "bernd.schmidt@logistikfirma.com",
        phoneNumber = "0123456790",
        password = "securepassword2",
        departmentId = "buchhaltung"
    )

    val user3 = User(

        firstName = "Claudia",
        lastName = "Weber",
        image = R.drawable.jennifer,
        email = "claudia.weber@logistikfirma.com",
        phoneNumber = "0123456791",
        password = "securepassword3",
        departmentId = "buchhaltung"
    )

    // Abteilung: Disponent
    val user4 = User(

        firstName = "Daniel",
        lastName = "Fischer",
        image = R.drawable.johnny,
        email = "daniel.fischer@logistikfirma.com",
        phoneNumber = "0123456792",
        password = "securepassword4",
        departmentId = "disponent"
    )

    val user5 = User(

        firstName = "Eva",
        lastName = "Schneider",
        image = R.drawable.keanu,
        email = "eva.schneider@logistikfirma.com",
        phoneNumber = "0123456793",
        password = "securepassword5",
        departmentId = "disponent"
    )

    val user6 = User(

        firstName = "Felix",
        lastName = "Bauer",
        image = R.drawable.leo,
        email = "felix.bauer@logistikfirma.com",
        phoneNumber = "0123456794",
        password = "securepassword6",
        departmentId = "disponent"
    )

    // Abteilung: Fahrer
    val user7 = User(

        firstName = "Günther",
        lastName = "Hofmann",
        image = R.drawable.margot,
        email = "guenther.hofmann@logistikfirma.com",
        phoneNumber = "0123456795",
        password = "securepassword7",
        departmentId = "fahrer"
    )

    val user8 = User(

        firstName = "Helga",
        lastName = "Schulz",
        image = R.drawable.reese,
        email = "helga.schulz@logistikfirma.com",
        phoneNumber = "0123456796",
        password = "securepassword8",
        departmentId = "fahrer"
    )

    val user9 = User(

        firstName = "Ingo",
        lastName = "Klein",
        image = R.drawable.sandra,
        email = "ingo.klein@logistikfirma.com",
        phoneNumber = "0123456797",
        password = "securepassword9",
        departmentId = "fahrer"
    )

    val user10 = User(

        firstName = "Jürgen",
        lastName = "Meyer",
        image = R.drawable.scarlett,
        email = "juergen.meyer@logistikfirma.com",
        phoneNumber = "0123456798",
        password = "securepassword10",
        departmentId = "fahrer"
    )

    val user11 = User(

        firstName = "Karin",
        lastName = "Lange",
        image = R.drawable.tom,
        email = "karin.lange@logistikfirma.com",
        phoneNumber = "0123456799",
        password = "securepassword11",
        departmentId = "fahrer"
    )

    val user12 = User(

        firstName = "Lars",
        lastName = "Wolff",
        image = R.drawable.will,
        email = "lars.wolff@logistikfirma.com",
        phoneNumber = "0123456700",
        password = "securepassword12",
        departmentId = "fahrer"
    )

    // Neue Mitarbeiter (ohne bisherige Kommunikation)
    val user13 = User(

        firstName = "Monika",
        lastName = "Becker",
        image = R.drawable.brad,
        email = "monika.becker@logistikfirma.com",
        phoneNumber = "0123456701",
        password = "securepassword13",
        departmentId = "buchhaltung"
    )

    val user14 = User(

        firstName = "Nico",
        lastName = "Zimmermann",
        image = R.drawable.emma,
        email = "nico.zimmermann@logistikfirma.com",
        phoneNumber = "0123456702",
        password = "securepassword14",
        departmentId = "disponent"
    )

    val user15 = User(

        firstName = "Olaf",
        lastName = "Fuchs",
        image = R.drawable.jennifer,
        email = "olaf.fuchs@logistikfirma.com",
        phoneNumber = "0123456703",
        password = "securepassword15",
        departmentId = "fahrer"
    )


    //---------------------------------------------
/*
    // Anna Müller -> Bernd Schmidt
    val message1_2_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_2",
        senderId = user1.userId,
        messageText = "Hallo Bernd, wie läuft es in der Buchhaltung?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_2_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_2",
        senderId = user2.userId,
        messageText = "Hallo Anna, alles gut, danke!",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Claudia Weber
    val message1_3_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_3",
        senderId = user1.userId,
        messageText = "Hallo Claudia, wie geht es dir?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_3_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_3",
        senderId = user3.userId,
        messageText = "Hi Anna, mir geht’s gut, danke!",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Daniel Fischer
    val message1_4_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_4",
        senderId = user1.userId,
        messageText = "Hallo Daniel, wie läuft die Disposition?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_4_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_4",
        senderId = user4.userId,
        messageText = "Hi Anna, alles im Griff hier.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Eva Schneider
    val message1_5_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_5",
        senderId = user1.userId,
        messageText = "Hallo Eva, könntest du mir die aktuellen Daten schicken?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_5_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_5",
        senderId = user5.userId,
        messageText = "Klar, schicke ich dir gleich.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Felix Bauer
    val message1_6_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_6",
        senderId = user1.userId,
        messageText = "Hallo Felix, wie ist der Plan für heute?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_6_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_6",
        senderId = user6.userId,
        messageText = "Hi Anna, der Plan ist schon fertig.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Günther Hofmann
    val message1_7_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_7",
        senderId = user1.userId,
        messageText = "Hallo Günther, wie ist der Stand der Dinge?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_7_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_7",
        senderId = user7.userId,
        messageText = "Alles in Ordnung, Anna.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Helga Schulz
    val message1_8_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_8",
        senderId = user1.userId,
        messageText = "Hallo Helga, hast du die Lieferung erhalten?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_8_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_8",
        senderId = user8.userId,
        messageText = "Ja, Anna, alles angekommen.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Ingo Klein
    val message1_9_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_9",
        senderId = user1.userId,
        messageText = "Hallo Ingo, wie sieht’s aus bei dir?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_9_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_9",
        senderId = user9.userId,
        messageText = "Alles in Ordnung, Anna.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Jürgen Meyer
    val message1_10_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_10",
        senderId = user1.userId,
        messageText = "Hallo Jürgen, wie läuft der Tag?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_10_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_10",
        senderId = user10.userId,
        messageText = "Ganz gut, danke Anna.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Karin Lange
    val message1_11_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_11",
        senderId = user1.userId,
        messageText = "Hallo Karin, alles in Ordnung?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_11_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_11",
        senderId = user11.userId,
        messageText = "Ja, alles bestens, Anna.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Anna Müller -> Lars Wolff
    val message1_12_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_12",
        senderId = user1.userId,
        messageText = "Hallo Lars, alles vorbereitet?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message1_12_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_1_12",
        senderId = user12.userId,
        messageText = "Ja, Anna, alles fertig.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Claudia Weber
    val message2_3_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_3",
        senderId = user2.userId,
        messageText = "Hallo Claudia, wie läuft's?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_3_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_3",
        senderId = user3.userId,
        messageText = "Ganz gut, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Daniel Fischer
    val message2_4_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_4",
        senderId = user2.userId,
        messageText = "Hallo Daniel, alles okay?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_4_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_4",
        senderId = user4.userId,
        messageText = "Ja, läuft alles, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Eva Schneider
    val message2_5_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_5",
        senderId = user2.userId,
        messageText = "Hallo Eva, kannst du mir helfen?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_5_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_5",
        senderId = user5.userId,
        messageText = "Ja, was brauchst du, Bernd?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Felix Bauer
    val message2_6_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_6",
        senderId = user2.userId,
        messageText = "Hallo Felix, wie geht’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_6_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_6",
        senderId = user6.userId,
        messageText = "Gut, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Günther Hofmann
    val message2_7_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_7",
        senderId = user2.userId,
        messageText = "Hallo Günther, wie ist es bei dir?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_7_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_7",
        senderId = user7.userId,
        messageText = "Alles gut, Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Helga Schulz
    val message2_8_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_8",
        senderId = user2.userId,
        messageText = "Hallo Helga, brauchst du Hilfe?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_8_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_8",
        senderId = user8.userId,
        messageText = "Nein, danke, alles gut.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Ingo Klein
    val message2_9_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_9",
        senderId = user2.userId,
        messageText = "Hallo Ingo, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_9_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_9",
        senderId = user9.userId,
        messageText = "Gut, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Jürgen Meyer
    val message2_10_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_10",
        senderId = user2.userId,
        messageText = "Hallo Jürgen, alles klar bei dir?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_10_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_10",
        senderId = user10.userId,
        messageText = "Ja, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Karin Lange
    val message2_11_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_11",
        senderId = user2.userId,
        messageText = "Hallo Karin, wie geht’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_11_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_11",
        senderId = user11.userId,
        messageText = "Gut, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Bernd Schmidt -> Lars Wolff
    val message2_12_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_12",
        senderId = user2.userId,
        messageText = "Hallo Lars, alles gut?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message2_12_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_2_12",
        senderId = user12.userId,
        messageText = "Ja, danke Bernd.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Daniel Fischer
    val message3_4_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_4",
        senderId = user3.userId,
        messageText = "Hallo Daniel, alles in Ordnung?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_4_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_4",
        senderId = user4.userId,
        messageText = "Ja, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Eva Schneider
    val message3_5_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_5",
        senderId = user3.userId,
        messageText = "Hallo Eva, kannst du mir helfen?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_5_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_5",
        senderId = user5.userId,
        messageText = "Ja, was brauchst du, Claudia?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Felix Bauer
    val message3_6_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_6",
        senderId = user3.userId,
        messageText = "Hallo Felix, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_6_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_6",
        senderId = user6.userId,
        messageText = "Gut, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Günther Hofmann
    val message3_7_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_7",
        senderId = user3.userId,
        messageText = "Hallo Günther, alles gut?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_7_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_7",
        senderId = user7.userId,
        messageText = "Ja, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Helga Schulz
    val message3_8_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_8",
        senderId = user3.userId,
        messageText = "Hallo Helga, brauchst du was?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_8_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_8",
        senderId = user8.userId,
        messageText = "Nein, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Ingo Klein
    val message3_9_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_9",
        senderId = user3.userId,
        messageText = "Hallo Ingo, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_9_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_9",
        senderId = user9.userId,
        messageText = "Gut, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Jürgen Meyer
    val message3_10_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_10",
        senderId = user3.userId,
        messageText = "Hallo Jürgen, alles klar?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_10_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_10",
        senderId = user10.userId,
        messageText = "Ja, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Karin Lange
    val message3_11_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_11",
        senderId = user3.userId,
        messageText = "Hallo Karin, alles gut?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_11_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_11",
        senderId = user11.userId,
        messageText = "Ja, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Claudia Weber -> Lars Wolff
    val message3_12_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_12",
        senderId = user3.userId,
        messageText = "Hallo Lars, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message3_12_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_3_12",
        senderId = user12.userId,
        messageText = "Gut, danke Claudia.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Eva Schneider
    val message4_5_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_5",
        senderId = user4.userId,
        messageText = "Hallo Eva, hast du kurz Zeit?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_5_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_5",
        senderId = user5.userId,
        messageText = "Ja, was gibt’s, Daniel?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Felix Bauer
    val message4_6_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_6",
        senderId = user4.userId,
        messageText = "Hallo Felix, alles klar?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_6_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_6",
        senderId = user6.userId,
        messageText = "Ja, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Günther Hofmann
    val message4_7_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_7",
        senderId = user4.userId,
        messageText = "Hallo Günther, alles gut?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_7_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_7",
        senderId = user7.userId,
        messageText = "Ja, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Helga Schulz
    val message4_8_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_8",
        senderId = user4.userId,
        messageText = "Hallo Helga, brauchst du was?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_8_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_8",
        senderId = user8.userId,
        messageText = "Nein, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Ingo Klein
    val message4_9_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_9",
        senderId = user4.userId,
        messageText = "Hallo Ingo, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_9_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_9",
        senderId = user9.userId,
        messageText = "Gut, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Jürgen Meyer
    val message4_10_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_10",
        senderId = user4.userId,
        messageText = "Hallo Jürgen, alles klar?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_10_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_10",
        senderId = user10.userId,
        messageText = "Ja, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Karin Lange
    val message4_11_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_11",
        senderId = user4.userId,
        messageText = "Hallo Karin, alles gut?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_11_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_11",
        senderId = user11.userId,
        messageText = "Ja, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Daniel Fischer -> Lars Wolff
    val message4_12_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_12",
        senderId = user4.userId,
        messageText = "Hallo Lars, alles klar?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message4_12_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_4_12",
        senderId = user12.userId,
        messageText = "Ja, danke Daniel.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Felix Bauer
    val message5_6_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_6",
        senderId = user5.userId,
        messageText = "Hallo Felix, alles in Ordnung?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_6_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_6",
        senderId = user6.userId,
        messageText = "Ja, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Günther Hofmann
    val message5_7_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_7",
        senderId = user5.userId,
        messageText = "Hallo Günther, brauchst du was?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_7_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_7",
        senderId = user7.userId,
        messageText = "Nein, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Helga Schulz
    val message5_8_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_8",
        senderId = user5.userId,
        messageText = "Hallo Helga, wie geht’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_8_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_8",
        senderId = user8.userId,
        messageText = "Gut, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Ingo Klein
    val message5_9_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_9",
        senderId = user5.userId,
        messageText = "Hallo Ingo, alles gut?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_9_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_9",
        senderId = user9.userId,
        messageText = "Ja, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Jürgen Meyer
    val message5_10_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_10",
        senderId = user5.userId,
        messageText = "Hallo Jürgen, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_10_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_10",
        senderId = user10.userId,
        messageText = "Gut, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Karin Lange
    val message5_11_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_11",
        senderId = user5.userId,
        messageText = "Hallo Karin, wie geht’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_11_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_11",
        senderId = user11.userId,
        messageText = "Gut, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    // Eva Schneider -> Lars Wolff
    val message5_12_1 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_12",
        senderId = user5.userId,
        messageText = "Hallo Lars, wie läuft’s?",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )

    val message5_12_2 = Message(
        messageId = UUID.randomUUID().toString(),
        chatRoomId = "chat_room_5_12",
        senderId = user12.userId,
        messageText = "Gut, danke Eva.",
        timestamp = System.currentTimeMillis(),
        messageStatus = "sent"
    )*/

    //-------------------------------------

    val department1 = Department(

        departmentName = "Buchhaltung"
    )

    val department2 = Department(

        departmentName = "Disponent"
    )

    val department3 = Department(

        departmentName = "Fahrer"
    )

    //------------------------------------------------

    // Max Mustermann (user1) -> Julia Müller (user2)
    val chatRoom1_2 = ChatRoom(

        chatRoomName = "chat_room_1_2",
        lastMessage = "Gut, danke Max.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Max Mustermann (user1) -> Claudia Weber (user3)
    val chatRoom1_3 = ChatRoom(

        chatRoomName = "chat_room_1_3",
        lastMessage = "Ja, danke Max.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Max Mustermann (user1) -> Daniel Fischer (user4)
    val chatRoom1_4 = ChatRoom(

        chatRoomName = "chat_room_1_4",
        lastMessage = "Danke, Daniel.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Max Mustermann (user1) -> Eva Schneider (user5)
    val chatRoom1_5 = ChatRoom(

        chatRoomName = "chat_room_1_5",
        lastMessage = "Ja, danke Eva.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Julia Müller (user2) -> Claudia Weber (user3)
    val chatRoom2_3 = ChatRoom(

        chatRoomName = "chat_room_2_3",
        lastMessage = "Ja, danke Julia.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Julia Müller (user2) -> Daniel Fischer (user4)
    val chatRoom2_4 = ChatRoom(

        chatRoomName = "chat_room_2_4",
        lastMessage = "Ja, danke Daniel.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Julia Müller (user2) -> Eva Schneider (user5)
    val chatRoom2_5 = ChatRoom(

        chatRoomName = "chat_room_2_5",
        lastMessage = "Ja, danke Eva.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Claudia Weber (user3) -> Daniel Fischer (user4)
    val chatRoom3_4 = ChatRoom(

        chatRoomName = "chat_room_3_4",
        lastMessage = "Ja, danke Claudia.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Claudia Weber (user3) -> Eva Schneider (user5)
    val chatRoom3_5 = ChatRoom(

        chatRoomName = "chat_room_3_5",
        lastMessage = "Ja, danke Claudia.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )

    // Daniel Fischer (user4) -> Eva Schneider (user5)
    val chatRoom4_5 = ChatRoom(

        chatRoomName = "chat_room_4_5",
        lastMessage = "Ja, danke Daniel.",
        lastActivityTimestamp = System.currentTimeMillis(),
        participants = listOf()
    )


    val userList = listOf(
        user1,
        user2,
        user3,
        user4,
        user5,
        user6,
        user7,
        user8,
        user9,
        user10,
        user11,
        user12,
        user13,
        user14,
        user15,
        )

    val departmentList = listOf(
        department1,
        department2,
        department3,
    )

    val chatRoomList = listOf(
        chatRoom1_2,
        chatRoom1_3,
        chatRoom1_4,
        chatRoom1_5,
        chatRoom2_3,
        chatRoom2_4,
        chatRoom2_5,
        chatRoom3_4,
        chatRoom3_5,
        chatRoom4_5)

    /*val messageList = listOf(
        message1_2_1,
        message1_2_2,
        message1_3_1,
        message1_3_2,
        message1_4_1,
        message1_4_2,
        message1_5_1,
        message1_5_2,
        message1_6_1,
        message1_6_2,
        message1_7_1,
        message1_7_2,
        message1_8_1,
        message1_8_2,
        message1_9_1,
        message1_9_2,
        message1_10_1,
        message1_10_2,
        message1_11_1,
        message1_11_2,
        message1_12_1,
        message1_12_2,
        message2_3_1,
        message2_3_2,
        message2_4_1,
        message2_4_2,
        message2_5_1,
        message2_5_2,
        message2_6_1,
        message2_6_2,
        message2_7_1,
        message2_7_2,
        message2_8_1,
        message2_8_2,
        message2_9_1,
        message2_9_2,
        message2_10_1,
        message2_10_2,
        message2_11_1,
        message2_11_2,
        message2_12_1,
        message2_12_2,
        message3_4_1,
        message3_4_2,
        message3_5_1,
        message3_5_2,
        message3_6_1,
        message3_6_2,
        message3_7_1,
        message3_7_2,
        message3_8_1,
        message3_8_2,
        message3_9_1,
        message3_9_2,
        message3_10_1,
        message3_10_2,
        message3_11_1,
        message3_11_2,
        message3_12_1,
        message3_12_2,
        message4_5_1,
        message4_5_2,
        message4_6_1,
        message4_6_2,
        message4_7_1,
        message4_7_2,
        message4_8_1,
        message4_8_2,
        message4_9_1,
        message4_9_2,
        message4_10_1,
        message4_10_2,
        message4_11_1,
        message4_11_2,
        message4_12_1,
        message4_12_2,
        message5_6_1,
        message5_6_2,
        message5_7_1,
        message5_7_2,
        message5_8_1,
        message5_8_2,
        message5_9_1,
        message5_9_2,
        message5_10_1,
        message5_10_2,
        message5_11_1,
        message5_11_2,
        message5_12_1,
        message5_12_2,
        )*/

}