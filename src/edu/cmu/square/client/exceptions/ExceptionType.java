package edu.cmu.square.client.exceptions;

public enum ExceptionType
{
	sessionTimeOut, 
	duplicateName, 
	authorization, 
	other, 
	constraintViolated, 
	selfDelete, 
	onlyOneTechnique,
	unknownProject,
	entityDoesNotExist,
	missingLink,
	tooFewToPrioritize, noUsersPrioritized, mailError, invalidEmail, duplicateEmail, titleMissing, descriptionMissing, parseError, emptyFile;
}
