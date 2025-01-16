-- Addresses
INSERT INTO ADDRESS (id, address_line1, address_line2, city, postal_code) VALUES
(1, 'Wroclaw Street', 'Apartment 10', 'Warsaw', '00-005'),
(2, 'Main Street 1', NULL, 'Warsaw', '00-001'),
(3, 'Five Acres Street 5', NULL, 'Cracow', '30-002'),
(4, 'Jerusalem Avenues 100', 'Apartment 12', 'Poznan', '60-300'),
(5, 'Green 25', 'Apartment 3', 'Gdansk', '80-400'),
(6, 'Market Street 8', 'Apartment 1', 'Wroclaw', '50-500'),
(7, 'New 15', 'Apartment 4', 'Lodz', '90-100');

-- Doctors
INSERT INTO DOCTOR (id, first_name, last_name, telephone_number, email, doctor_number, specialization, address_id) VALUES
(1, 'John', 'Smith', '123123123', 'john.smith@example.com', 'D001', 'SURGEON', 1),
(2, 'Anna', 'Brown', '234234234', 'anna.brown@example.com', 'D002', 'GP', 2),
(3, 'Peter', 'Green', '345345345', 'peter.green@example.com', 'D003', 'DERMATOLOGIST', 3),
(4, 'Mary', 'White', '456456456', 'mary.white@example.com', 'D004', 'GP', 4),
(5, 'Chris', 'Lewis', '567567567', 'chris.lewis@example.com', 'D005', 'OCULIST', 5);

-- Patients
INSERT INTO PATIENT (id, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id, age) VALUES
(1, 'Catherine', 'Moore', '678678678', 'catherine.moore@example.com', 'P001', '1985-05-15', 1, 38),
(2, 'Mark', 'Carter', '789789789', 'mark.carter@example.com', 'P002', '1992-10-25', 2, 31),
(3, 'Eve', 'Baker', '890890890', 'eve.baker@example.com', 'P003', '1990-03-10', 3, 33),
(4, 'Thomas', 'Simmons', '901901901', 'thomas.simmons@example.com', 'P004', '1982-07-30', 4, 41),
(5, 'Agnes', 'Newman', '012012012', 'agnes.newman@example.com', 'P005', '2000-02-20', 5, 25);

-- Treatments
INSERT INTO MEDICAL_TREATMENT (id, description, type) VALUES
(1, 'Abdominal ultrasound', 'USG'),
(2, 'Chest X-ray', 'RTG'),
(3, 'Blood test', 'Laboratory'),
(4, 'Heart ECG', 'Diagnostic'),
(5, 'Magnetic resonance', 'MRI');

-- Visits
INSERT INTO VISIT (id, description, time, doctor_id, patient_id, treatment_id) VALUES
(1, 'Checkup', '2025-01-01 10:00:00', 1, 1, 1),
(2, 'Cardiology visit', '2025-01-02 11:00:00', 2, 2, 2),
(3, 'Pediatric checkup', '2025-01-03 12:00:00', 3, 3, 3),
(4, 'Dermatology consultation', '2025-01-04 13:00:00', 4, 4, 4),
(5, 'Dental visit', '2025-01-05 14:00:00', 5, 5, 5),
(6, 'Surgical follow-up', '2025-01-06 15:00:00', 1, 1, 2),
(7, 'Cardiology consultation', '2025-01-07 09:30:00', 2, 1, 3),
(8, 'Pediatric visit', '2025-01-08 10:00:00', 3, 3, 4),
(9, 'Rehabilitation', '2025-01-09 11:00:00', 2, 4, 5),
(10, 'Dental checkup', '2025-01-10 12:00:00', 5, 5, 1),
(11, 'Dermatology follow-up', '2025-01-11 14:00:00', 4, 2, 2),
(12, 'Pediatric follow-up', '2025-01-12 15:00:00', 3, 2, 3),
(13, 'Cardiology visit', '2025-01-13 13:30:00', 2, 5, 4),
(14, 'Dental consultation', '2025-01-14 16:00:00', 1, 3, 5),
(15, 'Follow-up visit', '2025-01-15 17:00:00', 1, 4, 1);