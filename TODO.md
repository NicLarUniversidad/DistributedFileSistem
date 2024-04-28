Cifrado:

StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
textEncryptor.setPassword(myEncryptionPassword);

String myEncryptedText = textEncryptor.encrypt(myText);

Descifrado:

String plainText = textEncryptor.decrypt(myEncryptedText);