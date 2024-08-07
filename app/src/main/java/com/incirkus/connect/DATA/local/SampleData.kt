package com.incirkus.connect.DATA.local

import com.incirkus.connect.DATA.Model.Contact
import com.incirkus.connect.DATA.Model.Message
import com.incirkus.connect.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object SampleData {

    val sampleContacts = listOf(
        Contact(id = 1, name = "Alice Smith", image = R.drawable.brad, email = "alice@example.com", phoneNumber = "1234567890", password = "password123"),
        Contact(id = 2, name = "Bob Johnson", image = R.drawable.emma, email = "bob@example.com", phoneNumber = "1234567891", password = "password123"),
        Contact(id = 3, name = "Charlie Brown", image = R.drawable.jennifer, email = "charlie@example.com", phoneNumber = "1234567892", password = "password123"),
        Contact(id = 4, name = "David Wilson", image = R.drawable.johnny, email = "david@example.com", phoneNumber = "1234567893", password = "password123"),
        Contact(id = 5, name = "Eve Davis", image = R.drawable.keanu, email = "eve@example.com", phoneNumber = "1234567894", password = "password123"),
        Contact(id = 6, name = "Frank Clark", image = R.drawable.leo, email = "frank@example.com", phoneNumber = "1234567895", password = "password123"),
        Contact(id = 7, name = "Grace Lewis", image = R.drawable.margot, email = "grace@example.com", phoneNumber = "1234567896", password = "password123"),
        Contact(id = 8, name = "Hank Lee", image = R.drawable.reese, email = "hank@example.com", phoneNumber = "1234567897", password = "password123"),
        Contact(id = 9, name = "Ivy Walker", image = R.drawable.sandra, email = "ivy@example.com", phoneNumber = "1234567898", password = "password123"),
        Contact(id = 10, name = "Jack Hall", image = R.drawable.scarlett, email = "jack@example.com", phoneNumber = "1234567899", password = "password123"),
        Contact(id = 11, name = "Karen Young", image = R.drawable.tom, email = "karen@example.com", phoneNumber = "1234567800", password = "password123"),
        Contact(id = 12, name = "Leo King", image = R.drawable.will, email = "leo@example.com", phoneNumber = "1234567801", password = "password123"),
        Contact(id = 13, name = "Mia Wright", image = R.drawable.brad, email = "mia@example.com", phoneNumber = "1234567802", password = "password123"),
        Contact(id = 14, name = "Nina Scott", image = R.drawable.emma, email = "nina@example.com", phoneNumber = "1234567803", password = "password123"),
        Contact(id = 15, name = "Oscar Green", image = R.drawable.jennifer, email = "oscar@example.com", phoneNumber = "1234567804", password = "password123"),
        Contact(id = 16, name = "Paul Adams", image = R.drawable.johnny, email = "paul@example.com", phoneNumber = "1234567805", password = "password123"),
        Contact(id = 17, name = "Quinn Baker", image = R.drawable.keanu, email = "quinn@example.com", phoneNumber = "1234567806", password = "password123"),
        Contact(id = 18, name = "Ruby Gonzalez", image = R.drawable.leo, email = "ruby@example.com", phoneNumber = "1234567807", password = "password123"),
        Contact(id = 19, name = "Sam Hill", image = R.drawable.margot, email = "sam@example.com", phoneNumber = "1234567808", password = "password123"),
        Contact(id = 20, name = "Tina Martinez", image = R.drawable.reese, email = "tina@example.com", phoneNumber = "1234567809", password = "password123")
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
}