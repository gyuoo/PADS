# Base image
FROM python:3.12

# Set the working directory
WORKDIR /app

# Install required packages
COPY s103-linux.txt .
RUN pip install --no-cache-dir -r s103-linux.txt

# Copy application files
COPY . .

# Run the application
CMD ["python", "product_consumer.py"]
