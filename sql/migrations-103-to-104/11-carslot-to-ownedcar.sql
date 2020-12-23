# Add new key
ALTER TABLE OWNEDCAR
    ADD COLUMN personaId BIGINT NULL;
ALTER TABLE OWNEDCAR
    ADD CONSTRAINT FK_OWNEDCAR_PERSONA_personaId
        FOREIGN KEY (personaId) REFERENCES PERSONA (ID)
            ON DELETE CASCADE;

# Update values for new key
UPDATE OWNEDCAR OC
    INNER JOIN CARSLOT CS ON CS.id = OC.carSlotId
SET OC.personaId=CS.PersonaId
WHERE OC.personaId IS NULL;

ALTER TABLE OWNEDCAR
    MODIFY COLUMN personaId BIGINT NOT NULL;

# Remove old key
ALTER TABLE OWNEDCAR
    DROP FOREIGN KEY FK_OWNEDCAR_CARSLOT;
ALTER TABLE OWNEDCAR
    DROP INDEX FK_OWNEDCAR_CARSLOT;
ALTER TABLE OWNEDCAR
    DROP COLUMN carSlotId;

# Remove old table
DROP TABLE CARSLOT;

# Update achievement scripts that depend on carSlot
UPDATE ACHIEVEMENT
SET update_trigger=REPLACE(update_trigger, 'carSlot.getOwnedCar().', 'ownedCar.')
WHERE update_trigger LIKE '%carSlot.getOwnedCar().%';